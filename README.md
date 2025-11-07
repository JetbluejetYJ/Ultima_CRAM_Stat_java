
# 📊 Ultima_CRAM_Stat_java

A Java program to generate basic quality statistics from a CRAM file using HTSJDK.

---

## 🚀 Usage

```bash
# Compile
javac -cp htsjdk-<version>.jar Ultima_CRAM_Stat.java

# Run
java -cp .:htsjdk-<version>.jar Ultima_CRAM_Stat [path_to_cram_file]

# Example:
java -cp .:htsjdk-3.0.5.jar Ultima_CRAM_Stat sample1.cram
```
- On Windows, use `;` instead of `:` for the classpath:
  ```
  java -cp .;htsjdk-3.0.5.jar Ultima_CRAM_Stat sample1.cram
  ```

**Compile/Run Example**
```bash
# 1. Download htsjdk-3.0.5.jar (example)
wget https://repo1.maven.org/maven2/com/github/samtools/htsjdk/3.0.5/htsjdk-3.0.5.jar

# 2. Compile
javac -cp htsjdk-3.0.5.jar SQS_CRAM_Modified.java

# 3. Run
java -cp .:htsjdk-3.0.5.jar SQS_CRAM_Modified sample1.cram
```



---

## 📝 Description

This program processes a CRAM file and outputs a summary of key sequencing quality metrics, including:

- Total number of bases
- Total number of reads
- Base composition (A, T, G, C, N counts)
- N base percentage
- GC content percentage
- Q20 and Q30 base counts and percentages
- Average read length

The results are saved in a text file named `[Sample_Name].sqs`.

---

## 📂 Output Example

The output file will look like:

```
Sample Name: sample1
Total Bases: 1234567
Total Reads: 12345
N Percentage: 0.12%
GC Content: 48.76%
Q20 Percentage: 97.45%
Q30 Percentage: 92.13%
A base count: 300000
T base count: 310000
G base count: 310000
C base count: 300000
N base count: 1567
Q20 Bases: 1203456
Q30 Bases: 1137890
Average Read Length: 100.12
------------------------------------------------------
```

---

## ⚙️ Requirements

- Java 8 or higher
- [HTSJDK library](https://samtools.github.io/htsjdk/)

Download the latest htsjdk JAR from [Maven Central](https://search.maven.org/artifact/com.github.samtools/htsjdk).

---

## 🏷️ Arguments

| Argument              | Description                        |
|-----------------------|------------------------------------|
| `[path_to_cram_file]` | Path to the input CRAM file        |

---

## 💡 Notes

- The program expects a single CRAM file as input.
- The output summary file will be created in the current directory.
- Make sure the CRAM file is accessible and not corrupted.
- If you see a reference error, ensure the reference FASTA is available if required by your CRAM.

---

