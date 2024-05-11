import java.io.*;
import java.util.*;

public class HuffmanCodeEncode {
    private Map<List<Byte>, Integer> frequencyOfChar;

    private int sizeOfMyFile=0;
    private node root;

    private String codeString = "";
    private int n;
    private Map<List<Byte>, String> huffmanCode;
    private static int accumelator = 0;
    private static int sizeOfAck = 0;

    public HuffmanCodeEncode(String path, int n) throws IOException {
        int bufferSize = 1000000 - (1000000 % n);
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[bufferSize];
            int bytesRead;
            this.n = n;
            huffmanCode = new HashMap<>();
            frequencyOfChar = new HashMap<>();
            File fileIn = new File(path );

            // handle the name of output file

            String firstPart = "20010795."+ n+".";

            File fileOut = new File(fileIn.getParent()+"\\"+firstPart+fileIn.getName()+".hc");
            fileOut.getParentFile().mkdirs();
            BufferedOutputStream outPutFile = new BufferedOutputStream(new FileOutputStream(fileIn.getParent()+"\\"+firstPart+fileIn.getName()+".hc"));


            while ((bytesRead = bis.read(buffer)) != -1) {
                generateFrequency(bytesRead, buffer);
                sizeOfMyFile+=bytesRead;

            }

            encodeText();

            BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(path));

            writeMapInFile(outPutFile);

            writeCodeInFile(bis2, outPutFile);

            outPutFile.close();

            double sizeOut = fileOut.length();
            double sizeIn = fileIn.length();
            double ratio = (sizeOut/sizeIn);
            System.out.println("The compression ratio = "+ratio+" with n = "+this.n);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeMapInFile(BufferedOutputStream bufferedOutputStream) throws IOException {
        String s="ABDOFromFront";
        List<Byte> l1=new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            l1.add((byte) s.charAt(i));
        }
        huffmanCode.put(l1, Integer.toString(sizeOfMyFile));
        ObjectOutputStream outFile = new ObjectOutputStream(bufferedOutputStream);
        outFile.writeObject(huffmanCode);
    }

    private void writeCodeInFileHandler(String outCode, BufferedOutputStream outFile) throws IOException {
        for (char it : outCode.toCharArray()) {
            if (it == '1') {
               accumelator |= (1 << sizeOfAck);
            }
            sizeOfAck += 1;
            if (sizeOfAck == 8) {
                char ff= (char) accumelator;
                outFile.write((char)accumelator);
                sizeOfAck = 0;
                accumelator = 0;
            }
        }
    }

    private void writeCodeInFile(BufferedInputStream inputFile, BufferedOutputStream outFile) throws IOException {
        List<Byte> byteInput = new ArrayList<Byte>();
        int bufferSize = 1000000 - (1000000 % n);
        byte[] buffer = new byte[bufferSize];
        int bytesRead;
        int count = 0;
        while ((bytesRead = inputFile.read(buffer)) != -1) {
            for (int i = 0; i < bytesRead; i++) {
                byteInput.add(buffer[i]);
                count++;
                if (count == n) {
                    count = 0;
                    if (huffmanCode.containsKey(byteInput)) {
                        writeCodeInFileHandler((huffmanCode.get(byteInput)), outFile);
                        byteInput.clear();
                    }
                }
            }
        }
        if (sizeOfAck > 0)
            outFile.write(accumelator);
    }

    private void generateFrequency(int noOfBytes, byte buffer[]) {

        for (int i = 0; i < noOfBytes; i = i + n) {
            List<Byte> byteInput = new ArrayList<Byte>();
            for (int j = i; j < (i + n); j++) {
                if (j >= noOfBytes) break;
                byteInput.add(buffer[j]);
            }
            Integer freq = frequencyOfChar.getOrDefault(byteInput, 0);
            frequencyOfChar.put(byteInput, freq + 1);
        }
    }

    private void generateCode(String code, node inputNode) throws IOException {
        if (inputNode != null) {
            // base case leaf node
            if (inputNode.isLeaf()) {
                if (code.length() == 0) {
                    code = "0";
                }
                huffmanCode.put(inputNode.getStringCoded(), code);
            } else {
                // go to the right branch of node
                generateCode(code.concat("1"), inputNode.getRight());
                // go to the left branch of node
                generateCode(code.concat("0"), inputNode.getLeft());
            }
        }
    }

    // encode the string
    public void encodeText() throws IOException {
        PriorityQueue<node> queue = new PriorityQueue<>();
        // iterate over all frequency of character and insert all leaves nodes into the queue at first
        frequencyOfChar.forEach((str, freq) ->
                queue.add(new node(str, freq)));
        while (queue.size() > 1) {
            node left = queue.poll();
            node right = queue.poll();
            // make an internal node with left and right node and the frequency is the sum of frequency
            // of right and left
            node newNode = new node(left, right);
            queue.add(newNode);
        }
        if (queue.size() == 1) {
            // start with the root
            root = queue.poll();
            generateCode("", root);

        }
    }
}
