import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.security.SecureRandom
import java.net.URLEncoder

// https://ah-3scale-ansible-admin.app.rhdp.ocp.cloud.lab.eng.bos.redhat.com
def url = "https://startups-admin.3scale.net"
url = "https://3scale-admin.app.rhdp.ocp.cloud.lab.eng.bos.redhat.com"
def token = "a96c1296376a4901d90d69405265843b888b12649e827052fe67dcdb899c4c61"

//createSvc(url, token, "anyname", "anysysname")
startUpGet(url, token)

def createSvc(String adminBaseUrl, String token,  String name, String serviceSystemName) {
    def activeDocSpecCreateUrl = "${adminBaseUrl}/admin/api/services.json"
    def data = "access_token=${token}&name=${name}&system_name=${serviceSystemName}"
  
    
    makeRequestwithBody(activeDocSpecCreateUrl, data, 'POST')
}


def startUpGet(
    String adminBaseUrl,
    String token) {
    
    def slurper = new JsonSlurper()
    def apiStr = "${adminBaseUrl}/admin/api/services.json?access_token=${token}"
    println(" api call ${apiStr}");
    def httpConnection = new URL(apiStr).openConnection()

    assert httpConnection.responseCode == httpConnection.HTTP_OK
    slurper.parse(httpConnection.inputStream.newReader())
    println(" ----------------- ")
}


def makeRequestwithBody(url, body, method) {
    def post = new URL(url).openConnection();
    post.setRequestMethod(method)
    post.setDoOutput(true)
    post.setRequestProperty('Content-Type', 'application/x-www-form-urlencoded')
    post.setRequestProperty('Accept', 'application/json')
    post.getOutputStream().write(body.getBytes('UTF-8'))
    def responseCode = post.getResponseCode();
    if (responseCode != 200 && responseCode != 201) {
        println('Failed to update/create . HTTP response: ' + responseCode)
        assert false
    } else {
        println('updated/created successfully!')
    }
}