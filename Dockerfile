FROM tomcat:9.0

EXPOSE 8080

RUN rm -rf /usr/local/tomcat/webapps/*

COPY target/newsPlatform-NLP.war /usr/local/tomcat/webapps

ADD http://opennlp.sourceforge.net/models-1.5/en-token.bin /usr/local/tomcat/webapps/models/en-token.bin
ADD http://opennlp.sourceforge.net/models-1.5/en-ner-organization.bin /usr/local/tomcat/webapps/models/en-ner-organization.bin
ADD http://opennlp.sourceforge.net/models-1.5/en-ner-person.bin /usr/local/tomcat/webapps/models/en-ner-person.bin
ADD http://opennlp.sourceforge.net/models-1.5/en-ner-location.bin /usr/local/tomcat/webapps/models/en-ner-location.bin

ENV JAVA_OPTS="-Xmx2g"

CMD ["catalina.sh","run"]