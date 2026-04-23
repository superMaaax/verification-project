import zipfile, struct

def patch_class(data):
    # class文件格式：magic(4) + minor(2) + major(2) + ...
    if data[:4] != b'\xca\xfe\xba\xbe':
        return data  # 不是class文件，跳过
    major = struct.unpack('>H', data[6:8])[0]
    if major > 55:  # 高于Java 11
        print(f"  patching v{major} → v55")
        return data[:6] + struct.pack('>H', 55) + data[8:]
    return data

src = '/Users/xiangxinzhong/Code/verification-project/.tools/openjml/jmlruntime.jar'
dst = '/Users/xiangxinzhong/Code/verification-project/.tools/openjml/jmlruntime_java11.jar'

with zipfile.ZipFile(src, 'r') as zin, \
     zipfile.ZipFile(dst, 'w', zipfile.ZIP_DEFLATED) as zout:
    for item in zin.infolist():
        data = zin.read(item.filename)
        if item.filename.endswith('.class'):
            data = patch_class(data)
        zout.writestr(item, data)

print(f'Done → {dst}')
