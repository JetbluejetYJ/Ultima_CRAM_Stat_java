import htsjdk.samtools.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Ultima_CRAM_Stat {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: java Ultima_CRAM_Stat [path of cram file]");
        }

        String cramFilePath = args[0];
        String sampleName = new File(cramFilePath).getName().replaceFirst("[.][^.]+$", "");

        try {
            processCramFile(cramFilePath, sampleName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processCramFile(String cramFilePath, String sampleName) throws IOException {
        long totalBases = 0;
        long totalReads = 0;
        long totalLength = 0;
        Map<Character, Long> baseCounts = new HashMap<>();
        baseCounts.put('A', 0L);
        baseCounts.put('T', 0L);
        baseCounts.put('G', 0L);
        baseCounts.put('C', 0L);
        baseCounts.put('N', 0L);
        long q20Bases = 0;
        long q30Bases = 0;

        // CRAM 파일을 읽기 위한 htsjdk 사용
        try (SamReader reader = SamReaderFactory.makeDefault().open(new File(cramFilePath))) {
            for (SAMRecord record : reader) {
                String seq = record.getReadString();
                byte[] qual = record.getBaseQualities();

                // 리드 수 및 길이 계산
                totalReads++;
                int readLength = seq.length();
                totalLength += readLength;
                totalBases += readLength;

                // 염기 카운트
                for (char base : seq.toCharArray()) {
                    if (baseCounts.containsKey(base)) {
                        baseCounts.put(base, baseCounts.get(base) + 1);
                    }
                }

                // 품질 점수 계산
                for (byte qualityScore : qual) {
                    if (qualityScore >= 20) {
                        q20Bases++;
                    }
                    if (qualityScore >= 30) {
                        q30Bases++;
                    }
                }
            }
        }

        double avgReadLength = totalReads > 0 ? (double) totalLength / totalReads : 0;
        printSummaryToFile(sampleName, totalBases, totalReads, baseCounts, q20Bases, q30Bases, avgReadLength);
    }

    private static void printSummaryToFile(String sampleName, long totalBases, long totalReads, Map<Character, Long> baseCounts, long q20Bases, long q30Bases, double avgReadLength) throws IOException {
        String outputFile = sampleName + ".sqs";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            double q20Percentage = totalBases > 0 ? (q20Bases * 100.0 / totalBases) : 0;
            double q30Percentage = totalBases > 0 ? (q30Bases * 100.0 / totalBases) : 0;
            double nPercentage = totalBases > 0 ? (baseCounts.get('N') * 100.0 / totalBases) : 0;
            double gcContent = totalBases > 0 ? ((baseCounts.get('G') + baseCounts.get('C')) * 100.0 / totalBases) : 0;

            writer.write("Sample Name: " + sampleName + "\n");
            writer.write("Total Bases: " + totalBases + "\n");
            writer.write("Total Reads: " + totalReads + "\n");
            writer.write("N Percentage: " + nPercentage + "%\n");
            writer.write("GC Content: " + gcContent + "%\n");
            writer.write("Q20 Percentage: " + q20Percentage + "%\n");
            writer.write("Q30 Percentage: " + q30Percentage + "%\n");
            for (char base : baseCounts.keySet()) {
                writer.write(base + " base count: " + baseCounts.get(base) + "\n");
            }
            writer.write("Q20 Bases: " + q20Bases + "\n");
            writer.write("Q30 Bases: " + q30Bases + "\n");
            writer.write("Average Read Length: " + avgReadLength + "\n");
            writer.write("------------------------------------------------------\n");
        }
    }
}
