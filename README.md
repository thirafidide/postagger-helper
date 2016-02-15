# Preprocessing : POSTaggerHelper
## Ambil Semua Tag
perintah
```bash
 java POSTaggerHelper --get-tags corpus.tsv tags.txt
```
hasil ``NN, SC, VB, NNP, JJ, RB, IN, Z, CD, CC, PR, PRP, MD, FW, NEG, DT, NND, WH, SYM, RP, OD, X, UH, ``

## Olah korpus 
Pengolahan korpus agar sesuai dengan format stanford-parser :
* Setiap baris berisi satu kalimat
* Setiap kata dipasangkan dengan tag, dipisah dengan "_"
* Setiap kata dilakukan penggantian karakter " " dengan  "-"
* Kata lowercase, tag uppercase

perintah 
```bash 
java POSTaggerHelper --corpus-process corpus.tsv corpus.processed.txt
```

## Split corpus 
Korpus dipisah menjadi data training dan data testing, sesuai yang diberikan oleh soal

perintah
```bash 
java POSTaggerHelper --get-training-testing data 1000
```

# Buat .prop file
## Generate
perintah
```bash
java -classpath stanford-postagger/stanford-postagger.jar edu.stanford.nlp.tagger.maxent.MaxentTagger -genprops > props.prop
```
## Modify
```text
...
tagSeparator : _
openClassTags : NN, SC, VB, NNP, JJ, RB, IN, Z, CD, CC, PR, PRP, MD, FW, NEG, DT, NND, WH, SYM, RP, OD, X, UH
arch : arsitektur model
...
```
# .bat Helper
* Gunakan modelfactory.bat untuk membangun model sesuai data training
* Gunakan exp1-tester.bat untuk testing eksperimen 1
* Gunakan exp2-tester.bat untuk testing eksperimen 2
