build: site/index.html
site/index.html:
	asciidoctor -D site --backend=html5 -a source-highlighter=highlight.js content/*.adoc 
run: site/index.html
	open site/index.html
clean:
	-rm -rf site

.PHONY: clean build run

