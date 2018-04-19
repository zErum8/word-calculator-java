# word-calculator-java
Application for calculating words from text files input.

# Pushing to hub.docker.com and sloppy.io

Create a build and push to dockhub:

`docker build -t zerum8/word-calculator-java .`

`docker push zerum8/word-calculator-java`

Create project on sloppy side:

`sloppy start --var=domain:zerum8-wcj.sloppy.zone zerum8-wcj.json`
