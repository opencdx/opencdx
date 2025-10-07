FROM mongo:8.0.15

COPY scripts /home/mongodb/scripts
COPY mongod.conf /home/mongodb

WORKDIR /home/mongodb

RUN chmod +x /home/mongodb/scripts/*.sh

CMD ["/bin/bash", "/home/mongodb/scripts/run.sh"]