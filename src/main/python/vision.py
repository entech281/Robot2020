import sys

if __name__ == "__main__":
    import sensor, image, time, ubinascii, pyb, ustruct
elif __name__ == "__test__":
    import struct as ustruct

FILTER_RANGES = [(1, 87, -88, -10, -41, 44)]
SHOULD_SEND_IMAGES = True
DEBUG_SCALE = 0.3
FRAMES_PER_SEND = 20

AREA_COEF = 3
CONVEXITY_COEF = 4
LINEARITY_COEF = 3

def initialize():
    sensor.reset()
    sensor.set_pixformat(sensor.RGB565) # grayscale is faster (160x120 max on OpenMV-M7)
    #GRAYSCALE, RGB565,BAYER
    sensor.set_framesize(sensor.QVGA)
    sensor.set_auto_whitebal(False)
    sensor.set_auto_gain(False)
    sensor.set_auto_exposure(False, exposure_us=100) # make smaller to go faster
    sensor.skip_frames(time = 2000)

class CommManager:
    def __init__(self):
        pass

    def _write(self, data):
        sys.stdout.write(data)

    def _get_header(self, _type, size):
        return ustruct.pack(">BI", ord(_type), int(size))

    def send_string(self, data):
        header = self._get_header("D", len(data))
        self._write(header + bytes(data))

    def send_image_bytes(self, image_bytes):
        header = self._get_header("I",len(image_bytes))
        self._write(header + image_bytes)

class TargetData:
    def __init__(self, found = False, cx = -1, cy = -1, width = -1, fps = -1):
        self.found = found
        self.cx = cx
        self.cy = cy
        self.width = width
    def format(self):
        return " ".join([
            str(self.found),
            str(self.cx),
            str(self.cy),
            str(self.width)
            ])

class LoopTime:
    def __init__(self):
        self.start_ms = pyb.millis()
    def end(self):
        self.end_ms = pyb.millis()
    def get_time(self):
        return self.end_ms - self.start_ms

def get_populated_target_data(_blob):
    return TargetData(True, _blob.cx(), _blob.cy(), _blob.w())

class IntervalTicker:
    def __init__(self, interval):
        self.counter = 0
        self.interval = interval
    def tick(self):
        self.counter += 1
        if (self.counter % self.interval) == 0:
            self.counter=0
            return True
        else:
            return False

def highlight_found_target(img, blob):
    img.draw_rectangle( blob.rect(), color = (0, 0, 255), thickness = 3)
    img.draw_cross(blob.cx(), blob.cy(), color = (255, 0, 0), size = 10, thickness = 1)

if __name__ == "__main__":
    clock = time.clock()
    initialize()
    ROI = [0,0, sensor.width(), int(sensor.height()*2/3)]

def valid_target(_blob):
    return _blob.compactness() < 0.6

def blob_elongation_score(x):
    return -(x-0.79)**2

def score_blob(blob, max_area):
    return [AREA_COEF * blob.pixels() / max_area
        + CONVEXITY_COEF * blob.convexity()
        + LINEARITY_COEF * blob_elongation_score(blob.elongation())
        ][0]#Hack to break formula over many lines

def get_best_blob(blobs):
    max_area = max([blob.pixels() for blob in blobs])
    best_blob = max(blobs, key = lambda blob: score_blob(blob, max_area))
    return best_blob

def get_target_data(img):
    data = TargetData()
    blobs = []
    for blob in img.find_blobs( FILTER_RANGES ):
        if valid_target(blob):
            blobs.append(blob)

    if len(blobs) > 0:
        target_blob = get_best_blob(blobs)
        highlight_found_target(img, target_blob)
        data = get_populated_target_data(blob)
    return data

send_ticker = IntervalTicker(FRAMES_PER_SEND)

communication = CommManager()
if __name__ == "__main__":
    while(True):
        timer = LoopTime()
        clock.tick()
        img = sensor.snapshot()
        data = get_target_data(img)
        communication.send_string(data.format())
        communication.send_image_bytes(bytes(img))
        timer.end()
        #print(str(timer.get_time()))
