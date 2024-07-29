FROM aerokube/selenoid:latest-release

RUN apk update \
    && apk add docker

WORKDIR /etc/selenoid

COPY browsers.json .

EXPOSE 4444

CMD ["-conf", "/etc/selenoid/browsers.json"]
