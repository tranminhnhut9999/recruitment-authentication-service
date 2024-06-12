if [ -f .env ]; then
  export $(echo $(cat .env | sed 's/#.*//g'| xargs) | envsubst)
fi

mkdir -p /root/database/backup

DATE=`date +"%d_%m_%Y_%H%M"`
SQL_FILE=/root/database/backup/${DATE}.sql

docker exec vietdangdb /usr/bin/mysqldump -u ${MYSQL_USER} --password=${MYSQL_PASSWORD} ${MYSQL_DATABASE} > ${SQL_FILE}

sudo find /root/database/backup/. -mtime +7 -exec rm {} \;