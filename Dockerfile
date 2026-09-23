FROM runmymind/docker-android-sdk:ubuntu-standalone-sha-3adc0bc

RUN wget -q "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -O "awscliv2.zip" && \
    unzip -q awscliv2.zip && \
    ./aws/install && \
    rm -rf awscliv2.zip aws/ && \
    aws --version

ENV JAVA_OPTS "-Xmx12g -Xms12g -Dfile.encoding=UTF-8"

WORKDIR /src
