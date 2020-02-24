import sensor, image, time,pyb

FILE_NAME='stream.jpg'
def draw_lines(x, y):
    centerX = 80
    centerY = 60
    img.draw_line(x, y - 20, x, y + 20, color = (255, 0, 0), thickness = 2)
    img.draw_line(x - 20, y, x + 20, y, color = (255, 0, 0), thickness = 2)
    img.draw_line(centerX, centerY - 20, centerX, centerY + 20, color = (255, 255, 255), thickness = 1)
    img.draw_line(centerX - 20, centerY, centerX + 20, centerY, color = (255, 255, 255), thickness = 1)
sensor.reset()
sensor.set_pixformat(sensor.RGB565)
sensor.set_framesize(sensor.QQVGA)
sensor.skip_frames(time = 2000)
RANGES = [(9, 100, -128, -12, -47, 40)]
RANGES2 = [ (0,100,-100,100,-100,100)]
clock = time.clock()
sensor.set_auto_whitebal(False)
sensor.set_auto_gain(False)
sensor.set_auto_exposure(False, exposure_us=2)
last_frame=pyb.millis()
FRAME_DELAY=125
while(True):
    clock.tick()
    img = sensor.snapshot()
    target_found = False
    x = -1
    y = -1
    width = -1
    for b in img.find_blobs( RANGES ):
      if b.compactness() < 0.5:
          target_found = True
          x = b.cx()
          y = b.cy()
          width = abs(b.x() - b.w())
          height = abs(b.y() - b.h())
          draw_lines(x, y)
          img.draw_rectangle( b.rect(), color = (0, 0, 255), thickness = 1)
    if ( pyb.millis() - last_frame ) > FRAME_DELAY:
        img.save(FILE_NAME)
        last_frame = pyb.millis()
    output = str(target_found) + " " + str(x) + " " + str(y) + " " + str(width) + " " + str(clock.fps()) + " -"
    print(output)
