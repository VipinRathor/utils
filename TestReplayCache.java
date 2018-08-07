/*
* A simple program to initialize and test JDK replay cache.
*
* How to run:
*   /usr/jdk64/jdk1.8.0_112/bin/java -Dsun.security.krb5.debug=true  TestReplayCache
*   /usr/jdk64/jdk1.8.0_112/bin/java -Dsun.security.krb5.debug=true -Dsun.security.krb5.rcache=none TestReplayCache
* 
* References:
* https://www.programcreek.com/java-api-examples/?code=lambdalab-mirror/jdk8u-jdk/jdk8u-jdk-master/test/sun/security/krb5/auto/ReplayCachePrecise.java
* https://github.com/frohoff/jdk8u-dev-jdk/blob/master/test/sun/security/krb5/auto/ReplayCacheTest.java
*
*/

import sun.security.krb5.internal.KerberosTime;
import sun.security.krb5.internal.ReplayCache;
import sun.security.krb5.internal.rcache.AuthTimeWithHash;

public class TestReplayCache {

  private static int time(int x) {
    return (int)(System.currentTimeMillis()/1000) + x;
  }

  public static void main(String args[]) throws Exception {
    String client = "TestReplayCache@REALM.COM";
    String server = "knox/foo3.bar.com@REALM.COM";

    AuthTimeWithHash time1 = new AuthTimeWithHash(client, server, time(0), 0, "1234567891234560");
    AuthTimeWithHash time2 = new AuthTimeWithHash(client, server, time(0), 0, "1234567891234567");
    KerberosTime now = new KerberosTime(time(0)*1000L);

    System.out.println("Current Replay Cache type is : " + System.getProperty("sun.security.krb5.rcache"));
    ReplayCache rc = ReplayCache.getInstance(System.getProperty("sun.security.krb5.rcache"));
    rc.checkAndStore(now, time1);
    rc.checkAndStore(now, time2);
  }
}
