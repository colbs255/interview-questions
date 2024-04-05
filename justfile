build:
    asciidoctor -D site --backend=html5 -a source-highlighter=highlight.js content/*.adoc
run:
    xdg-open site/index.html
clean:
    -rm -rf site
