import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanCodeDecode {

    Map<List<Byte>, String> huffmanCode;
    Map<String, List<Byte>> huffmanDecode;
    int bufferSize = 0;
    int n;
    BufferedInputStream InFile;
    BufferedOutputStream outFile;

    private int AbdoFromBack=0;

    public HuffmanCodeDecode(String filePath) throws IOException, ClassNotFoundException {
        this.n = 1;
        bufferSize = 1000000 - (1000000 % n);
        huffmanCode = new HashMap<>();
        huffmanDecode = new HashMap<>();
        readData(filePath);
    }

    private void readData(String path) throws IOException, ClassNotFoundException {
        int bytesRead = 0;
        byte[] buffer = new byte[bufferSize];
        File fileIn = new File(path );
        // handle file output name

        String outFileName = fileIn.getName().substring(0, fileIn.getName().length() - 3);
        String outPath = fileIn.getParent()+"\\"+"extracted."+outFileName;
        InFile = new BufferedInputStream(new FileInputStream(path));
        outFile = new BufferedOutputStream(new FileOutputStream(outPath));
        // read map
        huffmanCode =  readMapFromFile(InFile);
        String s="ABDOFromFront";
        List<Byte> l1=new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            l1.add((byte) s.charAt(i));
        }
        AbdoFromBack=Integer.parseInt(huffmanCode.get(l1));
        huffmanDecode.remove(l1);
        createMapOfDecode(huffmanCode);

        String str = "";
        while ((bytesRead = InFile.read(buffer)) != -1) {
            for (int i = 0; i < bytesRead; i++) {
                str = decodeByte(buffer[i], str);
            }
        }
        outFile.close();
        InFile.close();
    }

    private void createMapOfDecode(Map<List<Byte>, String> inputMap) {
        for (Map.Entry<List<Byte>, String> entry : inputMap.entrySet()) {
            huffmanDecode.put(entry.getValue(), entry.getKey());
        }
    }

    private static Map<List<Byte>, String> readMapFromFile(BufferedInputStream bufferedInputStream) throws IOException, ClassNotFoundException {
            ObjectInputStream inFile = new ObjectInputStream(bufferedInputStream);
            return (Map<List<Byte>, String>) inFile.readObject();

    }

    private String decodeByte(byte input, String str) throws IOException {
        int count = 0;
        while (count < 8) {
            if ((input & (1 << count))!=0) {
                str += "1";
            } else {
                str += "0";
            }
            count += 1;
            if (huffmanDecode.containsKey(str)) {
                List<Byte> c2 = (huffmanDecode.get(str));
                str = "";
                for (Byte it : c2) {
                    if(AbdoFromBack>0) {
                        outFile.write(it);
                        AbdoFromBack--;
                    }
                }
            }
        }
        return str;
    }

}
