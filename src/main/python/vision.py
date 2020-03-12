import sensor, image, time, ubinascii

DEFAULT_TRANSMIT = "False -1 -1 -1 0 -"
FILTER_RANGES = [(1, 87, -88, -10, -41, 44)]
SHOULD_SEND_IMAGES = False
FRAMES_PER_SEND = 50

def initialize():
    sensor.reset()
    sensor.set_pixformat(sensor.RGB565) # grayscale is faster (160x120 max on OpenMV-M7)
    #GRAYSCALE, RGB565,BAYER
    sensor.set_framesize(sensor.QVGA)
    sensor.set_auto_whitebal(False)
    sensor.set_auto_gain(False)
    sensor.set_auto_exposure(False, exposure_us=100) # make smaller to go faster
    sensor.skip_frames(time = 2000)

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

def get_populated_target_data(_blob):
    return TargetData(True, _blob.cx(), _blob.cy(), _blob.w())

def valid_target(_blob):
    return _blob.compactness() < 0.5

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

def transmit_data(data, fps):
    print(data.format() + " " + str(fps) + " - ")

def send_image(img):
    to_send = img.crop(ROI, copy_to_fb=True).compress(quality=50)
    print("Q",str(ubinascii.b2a_base64(to_send)))

def highlight_found_target(img, blob):
    img.draw_cross(blob.cx(), blob.cy(), color = (255, 0, 0), size = 10, thickness = 1)
    img.draw_rectangle( blob.rect(), color = (0, 0, 255), thickness = 3)

clock = time.clock()
initialize()

ROI = [0,0, sensor.width(), int(sensor.height()*2/3)]

def get_target_data(img):
    data = TargetData()
    blobs = []
    for blob in img.find_blobs( FILTER_RANGES, False, roi = ROI ):
        if valid_target(blob):
            blobs.append(blob)
    if len(blobs) > 0:
        target_blob = max(blobs, key = lambda blob: blob.pixels())
        highlight_found_target(img, target_blob)
        data = get_populated_target_data(blob)
    return data

send_ticker = IntervalTicker(FRAMES_PER_SEND)
while(True):
    clock.tick()
    img = sensor.snapshot()

    data = get_target_data(img)

    transmit_data(data, clock.fps())

    if SHOULD_SEND_IMAGES and send_ticker.tick():
        send_image(img)
