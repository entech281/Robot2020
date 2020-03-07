import sensor, image, time

def draw_lines(x, y):
    centerX = 80
    centerY = 60
    img.draw_line(x, y - 20, x, y + 20, color = (255, 0, 0), thickness = 1)
    img.draw_line(x - 20, y, x + 20, y, color = (255, 0, 0), thickness = 1)

    img.draw_line(centerX, centerY - 20, centerX, centerY + 20, color = (255, 0, 0), thickness = 1)
    img.draw_line(centerX - 20, centerY, centerX + 20, centerY, color = (255, 0, 0), thickness = 1)

def initialize():
    sensor.reset()
    sensor.set_pixformat(sensor.RGB565) # grayscale is faster (160x120 max on OpenMV-M7)
    #GRAYSCALE, RGB565,BAYER
    sensor.set_framesize(sensor.QQVGA)
    sensor.set_auto_whitebal(False)
    sensor.set_auto_gain(False)
    sensor.set_auto_exposure(False, exposure_us=100) # make smaller to go faster
    sensor.skip_frames(time = 2000)

def transmit_data(data):
    print(" ".join(data))

def valid_target(blob):
    return blob.compactness() < 0.5
    
FILTER_RANGES = [(1, 87, -88, -10, -41, 44)]
DEFAULT_TRANSMIT = "False -1 -1 -1 0 -"

clock = time.clock()

def gather_data(blob):
    global clock
    return {str(True), str(blob.cx()), str(blob.cy()), str(blob.w()), str(clock.fps()), "-"}

initialize()
print("Script started!")
while(True):
    clock.tick()
    img = sensor.snapshot()
    found_valid_target = False
    for b in img.find_blobs( FILTER_RANGES ):
        if valid_target(b):
            data = gather_data(b)
            transmit_data(data)
            draw_lines(b.cx(), b.cy())
            img.draw_rectangle( b.rect(), color = (0, 0, 255), thickness = 3)
            found_valid_target = True 
    if not found_valid_target:
        print(DEFAULT_TRANSMIT)
    