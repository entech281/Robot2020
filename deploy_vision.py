from serial import Serial
from serial.tools import list_ports


deploy_txt = ""
with open("src/main/python/vision.py", "r") as file:
    deploy_text = file.read().replace("    ", "\t").replace("\r\n","\n")

deploy_file_name = "test.py"
openMV_script = "open('test.py','w').write(\"\"\"{0}\"\"\")" % deploy_text.encode("utf-8")
openMV_script = openMV_script.encode("utf-8")

for serial_port in list_ports.comports(include_links = True):
    print(f"Trying device {serial_port.device}.")
    with serial.Serial(serial_port.device, 19200, timeout=1) as openMV:
        try:
            openMV.write(b"\x03")
            print("Interrupted running script...")
        except:
            print(f"Could not deploy to {serial_port.device}")
            continue
        print("Waiting for Python terminal...")
        openMV.read_until(expected=b">>>", size=None)
        print(f"Writing file {deploy_file_name} on openMV...")
        openMV.write(openMV_script)
        openMV.read_until(expected=b">>>", size=None)
        print("Starting main.py")
        openMV.write("exec(open('main.py','r').read())")
        print("Deployed!")
        break