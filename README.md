# Gunakan POSTaggerHelper
	## Ambil semua tag
		Perintah : java POSTaggerHelper --get-tags corpus.tsv tags.txt
		hasil : NN, SC, VB, NNP, JJ, RB, IN, Z, CD, CC, PR, PRP, MD, FW, NEG, DT, NND, WH, SYM, RP, OD, X, UH, 
	## Olah korpus agar sesuai dengan format stanford-parser
		format :
			> setiap baris berisi satu kalimat
			> setiap kata dipasangkan dengan tag, dipisah dengan "_"
			> setiap kata dilakukan penggantian karakter " " dengan  "-"
			> kata lowercase, tag uppercase
		perintah : java POSTaggerHelper --corpus-process corpus.tsv corpus.processed.txt
	## Split corpus menjadi data training dan data testing, sesuai yang diberikan oleh soal
		perintah : java POSTaggerHelper --get-training-testing data 1000

# Buat .prop file
	## generate
		perintah : java -classpath stanford-postagger/stanford-postagger.jar edu.stanford.nlp.tagger.maxent.MaxentTagger -genprops > props.prop
	## modify
		tagSeparator : _
		openClassTags : NN, SC, VB, NNP, JJ, RB, IN, Z, CD, CC, PR, PRP, MD, FW, NEG, DT, NND, WH, SYM, RP, OD, X, UH
		arch : arsitektur model

# Gunakan modelfactory.bat untuk membangun model sesuai data training

# Gunakan exp1-tester.bat untuk testing eksperimen 1

# Gunakan exp2-tester.bat untuk testing eksperimen 2