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
def url = "https://mss-3scale-admin.app.rhdp.ocp.cloud.lab.eng.bos.redhat.com"
def token = "215269fb901e616538320d13a0c74b8955bab3bc873937bb012f76cddea5fe98"

//createSvc(params.THREESCALE_URL, params.API_TOKEN, "anyname", "anysysname")
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