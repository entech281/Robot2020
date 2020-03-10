import sensor, image, time, pyb, uos

DEFAULT_TRANSMIT = "False -1 -1 -1 0 -"
FILTER_RANGES = [(1, 87, -88, -10, -41, 44)]
SHOULD_SAVE_IMAGES = False
FRAMES_PER_SAVE = 50
SAVE_PATH = "./saves"

def initialize():
    sensor.reset()
    sensor.set_pixformat(sensor.RGB565) # grayscale is faster (160x120 max on OpenMV-M7)
    #GRAYSCALE, RGB565,BAYER
    sensor.set_framesize(sensor.QVGA)
    sensor.set_auto_whitebal(False)
    sensor.set_auto_gain(False)
    sensor.set_auto_exposure(False, exposure_us=100) # make smaller to go faster
    sensor.skip_frames(time = 2000)
    try:
        for file in uos.listdir(SAVE_PATH):
            print(SAVE_PATH + "/" + file)
            uos.remove(SAVE_PATH + "/" + file)
        uos.rmdir(SAVE_PATH)
    except Exception as e:
        print(e)
    uos.mkdir(SAVE_PATH)


def valid_target(blob):
    return blob.compactness() < 0.5

clock = time.clock()

def gather_data(blob):
    global clock
    return [str(True), str(blob.cx()), str(blob.cy()), str(blob.w()), str(clock.fps())]

def get_default_data():
    global clock
    return [str(False), str(-1), str(-1), str(-1), str(clock.fps())]

def transmit_data(data):
    print(" ".join(data) + " - ")

def highlight_found_target(img, blob):
    img.draw_cross(blob.cx(), blob.cy(), color = (255, 0, 0), size=10, thickness = 1)
    img.draw_rectangle( blob.rect(), color = (0, 0, 255), thickness = 3)

counter = 0
def should_send_frame():
    global counter
    if SHOULD_SAVE_IMAGES:
        counter += 1
        return counter % FRAMES_PER_SAVE == 0
    else:
        return False

initialize()

ROI = [0,0, sensor.width(), int(sensor.height()/2)]

while(True):

    clock.tick()
    img = sensor.snapshot()

    data = get_default_data()
    num_blobs = 0
    num_targets = 0

    for blob in img.find_blobs( FILTER_RANGES, False, ROI ):
        num_blobs += 1
        if valid_target(blob):
            data = gather_data(blob)
            highlight_found_target(img, blob)
            num_targets += 1

    transmit_data(data)

    if should_send_frame():
        filename = SAVE_PATH + "/vision" + "-".join([ str(pyb.millis()), str(num_blobs), str(num_targets)])+".jpg"
        img.save(filename, ROI)
