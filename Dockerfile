FROM mozilla/sbt

WORKDIR /opt/app/

COPY . .

RUN chmod a+x scrmabled-strings
RUN sbt clean assembly

ENTRYPOINT ["./scrmabled-strings"]