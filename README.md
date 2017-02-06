This example is based on the official documentation from ;
- http://gatling.io
- https://jenkins.io/

Sequence diagram displaying functionality

![alt text](https://github.com/rajanarkenbout/nginx_couchbase/blob/master/nginx_couchbase_sequence_diagram.jpg "Nginx couchbase sequence diagram")

Ensure Gatling is installed on your Jenkins (master/slave) nodes

``` bash
$ mkdir /opt/gatling/
$ wget https://repo1.maven.org/maven2/io/gatling/highcharts/gatling-charts-highcharts-bundle/2.2.3/gatling-charts-highcharts-bundle-2.2.3-bundle.zip -P /opt/gatling/
$ unzip -o /opt/gatling/gatling-charts-highcharts-bundle-2.2.3-bundle.zip -d /opt/gatling
```

Install the Jenkins gatling plugin

- https://wiki.jenkins-ci.org/display/JENKINS/Gatling+Plugin

Create a pipeline job in Jenkins

![alt text](https://github.com/rajanarkenbout/gatling-csv-input/blob/master/jenkins-config.png "Jenkins pipeline job and git config")

Pipeline job post configuration

![alt text](https://github.com/rajanarkenbout/gatling-csv-input/blob/master/jenkins-java-options.png "Jenkins pipeline job and java options")





Installing Nginx with all modules

``` bash
# Here we assume Nginx is to be installed under /opt/nginx/.
./configure --prefix=/opt/nginx \
 --with-ld-opt="-Wl,-rpath,/usr/local/lib" \
 --with-http_ssl_module \
 --add-module=/home/r.arkenbout/ngx_devel_kit-0.3.0 \
 --add-module=/home/r.arkenbout/lua-nginx-module-0.10.7 \
 --add-module=/home/r.arkenbout/memc-nginx-module-0.17 \
 --add-module=/home/r.arkenbout/echo-nginx-module-0.60 \
 --add-module=/home/r.arkenbout/srcache-nginx-module-0.31 \
 --add-module=/home/r.arkenbout/set-misc-nginx-module-0.31
make -j2
make install
```

A simple init script: vi /etc/init.d/nginx

``` bash
#!/bin/sh
#
# nginx - this script starts and stops the nginx daemon
#
# chkconfig:   - 85 15
# description:  Nginx is an HTTP(S) server, HTTP(S) reverse \
#               proxy and IMAP/POP3 proxy server
# processname: nginx
# config:      /etc/nginx/nginx.conf
# config:      /etc/sysconfig/nginx
# pidfile:     /var/run/nginx.pid

# Source function library.
. /etc/rc.d/init.d/functions

# Source networking configuration.
. /etc/sysconfig/network

# Check that networking is up.
[ "$NETWORKING" = "no" ] && exit 0

nginx="/usr/sbin/nginx"
prog=$(basename $nginx)

sysconfig="/etc/sysconfig/$prog"
lockfile="/var/lock/subsys/nginx"
pidfile="/var/run/${prog}.pid"

NGINX_CONF_FILE="/etc/nginx/nginx.conf"

[ -f $sysconfig ] && . $sysconfig


start() {
    [ -x $nginx ] || exit 5
    [ -f $NGINX_CONF_FILE ] || exit 6
    echo -n $"Starting $prog: "
    daemon $nginx -c $NGINX_CONF_FILE
    retval=$?
    echo
    [ $retval -eq 0 ] && touch $lockfile
    return $retval
}

stop() {
    echo -n $"Stopping $prog: "
    killproc -p $pidfile $prog
    retval=$?
    echo
    [ $retval -eq 0 ] && rm -f $lockfile
    return $retval
}

restart() {
    configtest_q || return 6
    stop
    start
}

reload() {
    configtest_q || return 6
    echo -n $"Reloading $prog: "
    killproc -p $pidfile $prog -HUP
    echo
}

configtest() {
    $nginx -t -c $NGINX_CONF_FILE
}

configtest_q() {
    $nginx -t -q -c $NGINX_CONF_FILE
}

rh_status() {
    status $prog
}

rh_status_q() {
    rh_status >/dev/null 2>&1
}

# Upgrade the binary with no downtime.
upgrade() {
    local oldbin_pidfile="${pidfile}.oldbin"

    configtest_q || return 6
    echo -n $"Upgrading $prog: "
    killproc -p $pidfile $prog -USR2
    retval=$?
    sleep 1
    if [[ -f ${oldbin_pidfile} && -f ${pidfile} ]];  then
        killproc -p $oldbin_pidfile $prog -QUIT
        success $"$prog online upgrade"
        echo
        return 0
    else
        failure $"$prog online upgrade"
        echo
        return 1
    fi
}

# Tell nginx to reopen logs
reopen_logs() {
    configtest_q || return 6
    echo -n $"Reopening $prog logs: "
    killproc -p $pidfile $prog -USR1
    retval=$?
    echo
    return $retval
}

case "$1" in
    start)
        rh_status_q && exit 0
        $1
        ;;
    stop)
        rh_status_q || exit 0
        $1
        ;;
    restart|configtest|reopen_logs)
        $1
        ;;
    force-reload|upgrade)
        rh_status_q || exit 7
        upgrade
        ;;
    reload)
        rh_status_q || exit 7
        $1
        ;;
    status|status_q)
        rh_$1
        ;;
    condrestart|try-restart)
        rh_status_q || exit 7
        restart
	    ;;
    *)
        echo $"Usage: $0 {start|stop|reload|configtest|status|force-reload|upgrade|restart|reopen_logs}"
        exit 2
esac

```

- post configuration

``` bash
$ chmod 755 /etc/init.d/nginx
$ cp /opt/nginx/sbin/nginx /usr/sbin/
$ mkdir /etc/nginx/
$ cp -r conf/* /etc/nginx/
```

- nginx configuration: copy nginx.conf to /etc/nginx

- starting nginx

``` bash
$ /etc/init.d/nginx start
$ pkill -f nginx && /etc/init.d/nginx start
```

- Executing tests using Postman:

```
http://yournodename/id/id/id

Invalidation
http://yournodename/id/id/id
+ header invalidator = true
```
