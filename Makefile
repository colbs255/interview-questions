build: build/index.html
build/index.html:
	asciidoctor -D build --backend=html5 -a source-highlighter=highlight.js content/*.adoc 
run: build/index.html
	open build/index.html
clean:
	-rm -rf build

.PHONY: clean build run

