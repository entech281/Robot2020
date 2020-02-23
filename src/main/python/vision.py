# Grab JPG to disk
#
# Welcome to the OpenMV IDE! Click on the green run arrow button below to run the script!

import sensor, image, time

sensor.reset()                      # Reset and initialize the sensor.
sensor.set_pixformat(sensor.RGB565) # Set pixel format to RGB565 (or GRAYSCALE)
sensor.set_framesize(sensor.HQVGA)   # Set frame size to QVGA (320x240)

sensor.set_quality(50)
sensor.set_auto_exposure(False,200)
sensor.skip_frames(time = 1000)     # Wait for settings take effect.
sensor.set_auto_whitebal(False)
sensor.set_auto_gain(False)
clock = time.clock()                # Create a clock object to track the FPS.
last_frame = time.ticks()           # grab frames periodically
#grab_rate_hz = 30
#grab_delay_ms = 1000/grab_rate_hz
grab_delay_ms = 20
while(True):
    clock.tick()                    # Update the FPS clock.
    img = sensor.snapshot()         # Take a picture and return the image.

                                    # to the IDE. The FPS should increase once disconnected.

    if ( time.ticks() - last_frame ) > grab_delay_ms :
        #img_writer = image.ImageWriter("/stream.jpg")
        #img_writer.add_frame(img)
        #img_writer.close()
        img.save("/test.jpg")
        print("Grab")
        last_frame = time.ticks()
    print(clock.fps())              # Note: OpenMV Cam runs about half as fast when connected
