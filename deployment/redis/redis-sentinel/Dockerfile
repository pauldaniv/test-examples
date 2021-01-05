FROM redis:3.2

RUN mkdir -p /etc/redis/log

ADD entrypoint.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/entrypoint.sh

ENTRYPOINT ["entrypoint.sh"]
CMD ["standalone"]
