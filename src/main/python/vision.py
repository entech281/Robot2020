import sensor, image, time

def draw_lines(x, y):
    centerX = 80
    centerY = 60
    img.draw_line(x, y - 20, x, y + 20, color = (255, 0, 0), thickness = 5)
    img.draw_line(x - 20, y, x + 20, y, color = (255, 0, 0), thickness = 5)

    img.draw_line(centerX, centerY - 20, centerX, centerY + 20, color = (255, 0, 0), thickness = 5)
    img.draw_line(centerX - 20, centerY, centerX + 20, centerY, color = (255, 0, 0), thickness = 5)

def initialize():
    sensor.reset()
    sensor.set_pixformat(sensor.RGB565) # grayscale is faster (160x120 max on OpenMV-M7)
    #GRAYSCALE, RGB565,BAYER
    sensor.set_framesize(sensor.QQVGA)
    sensor.skip_frames(time = 2000)
    sensor.set_auto_whitebal(False)
    sensor.set_auto_gain(False)
    sensor.set_auto_exposure(False, exposure_us=100) # make smaller to go faster

def gather_data(blob):
    return (True, b.cx(), b.cy(), width, clock.fps())

def transmit_data(data):
    #print(str(data[0]) + " " +  str(data[1]) + " " str(data[2]) + " " + str(data[3]) + " " + str(data[4]) + " -")
    print(data)

def valid_target(blob):
    return b.compactness() < 0.5


FILTER_RANGES = [(9, 100, -128, -12, -47, 40)]
DEFAULT_TRANSMIT = "False -1 -1 -1 0 -"

clock = time.clock()
while(True):
    clock.tick()
    img = sensor.snapshot()
    for b in img.find_blobs( FILTER_RANGES ):
        if valid_target(b):
            data = gather_data(b)
            transmit_data(data)
            draw_lines(x, y)
            img.draw_rectangle( b.rect(), color = (0, 0, 255), thickness = 3)
    output = str(target_found) + " " + str(x) + " " + str(y) + " " + str(width) + " " + str(clock.fps()) + " -"
    print(output)
