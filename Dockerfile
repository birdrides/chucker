FROM runmymind/docker-android-sdk:ubuntu-standalone-sha-3adc0bc

ENV JAVA_OPTS "-Xmx12g -Xms12g -Dfile.encoding=UTF-8"

ADD firebaseMaster.sh ./firebaseMaster.sh
ADD firebaseNightly.sh ./firebaseNightly.sh

WORKDIR /src
