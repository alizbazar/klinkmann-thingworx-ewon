/**
 * This class provided a REST implementation for J2ME.
 * @author Christoph Hartmann
 */

// TODO: This might need some packages to be imported in order to work

public class RestClient {
    String userid;
    String password;
    // User Agent
    private String ua;

    public RestClient() {}

    public RestClient(String userid, String password) {
        this.userid = userid;
        this.password = password;
        ua = "Profile/" + System.getProperty("microedition.profiles")
                + " Configuration/"
                + System.getProperty("microedition.configuration");
    }

    /**
     * prepare the HTTP connection
     * @param conn
     * @throws IOException
     */
    private void configureConncetion(HttpConnection conn) throws IOException {
        conn.setRequestProperty("User-Agent", ua);
        String locale = System.getProperty("microedition.locale");
        if (locale == null) { locale = "en-US"; }
        conn.setRequestProperty("Accept-Language", locale);
        conn.setRequestProperty("Content-Type", "text/json");
        conn.setRequestProperty("Accept", "text/plain");
        conn.setRequestProperty("appKey", "929e65a6-7a88-4c4c-a75b-d40d18cdabbb");
    }

    public HttpConnection getConnection(String url) throws IOException {
        HttpConnection conn = (HttpConnection) Connector.open(url);
        configureConncetion(conn);
        return conn;
    }

    public HttpConnection getConnection(String url, int access) throws IOException {
        HttpConnection conn = (HttpConnection) Connector.open(url, access);
        configureConncetion(conn);
        return conn;
    }

    /**
     * READ
     * @param url
     * @return
     * @throws IOException
     */
    public String get(String url) throws IOException {
        HttpConnection hcon = null;
        DataInputStream dis = null;
        StringBuffer responseMessage = new StringBuffer();

        try {
            int redirectTimes = 0;
            boolean redirect;

            do {
                redirect = false;

                // a standard HttpConnection with READ access
                hcon = getConnection(url);
                // obtain a DataInputStream from the HttpConnection
                dis = new DataInputStream(hcon.openInputStream());
                // retrieve the response from the server
                int ch;
                while ((ch = dis.read()) != -1) {
                    responseMessage.append((char) ch);
                }// end while ( ( ch = dis.read() ) != -1 )
                // check status code
                int status = hcon.getResponseCode();

                switch (status) {
                case HttpConnection.HTTP_OK: // Success!
                    break;
                case HttpConnection.HTTP_TEMP_REDIRECT:
                case HttpConnection.HTTP_MOVED_TEMP:
                case HttpConnection.HTTP_MOVED_PERM:
                    // Redirect: get the new location
                    url = hcon.getHeaderField("location");
                    System.out.println("Redirect: " + url);

                    if (dis != null) dis.close();
                    if (hcon != null) hcon.close();

                    hcon = null;
                    redirectTimes++;
                    redirect = true;
                    break;
                default:
                    // Error: throw exception
                    hcon.close();
                    throw new IOException("Response status not OK:" + status);
                }

                // max 5 redirects
            } while (redirect == true && redirectTimes < 5);

            if (redirectTimes == 5) {
                throw new IOException("Too much redirects");
            }

        } catch (Exception e) {
            e.printStackTrace();
            // TODO bad style
            responseMessage.append("ERROR: ");
        } finally {
            try {
                if (hcon != null)
                    hcon.close();
                if (dis != null)
                    dis.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }// end try/catch
        }// end try/catch/finally
        return responseMessage.toString();

    }// end sendGetRequest( String )

    /**
     * UPDATE
     *
     * @param url
     * @param data
     *            the request body
     * @throws IOException
     */
    public String post(String url, String data) throws IOException {

        HttpConnection hcon = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        StringBuffer responseMessage = new StringBuffer();

        try {
            int redirectTimes = 0;
            boolean redirect;

            do {
                redirect = false;
                // an HttpConnection with both read and write access
                hcon = getConnection(url, Connector.READ_WRITE);
                // set the request method to POST
                hcon.setRequestMethod(HttpConnection.POST);
                // overwrite content type to be form based
                hcon.setRequestProperty("Content-Type", "application/json");
                // set message length
                if (data != null) {
                    hcon.setRequestProperty("Content-Length", ""
                            + data.length());
                }

                if (data != null) {
                    // obtain DataOutputStream for sending the request string
                    dos = hcon.openDataOutputStream();
                    byte[] request_body = data.getBytes();
                    // send request string to server
                    for (int i = 0; i < request_body.length; i++) {
                        dos.writeByte(request_body[i]);
                    }// end for( int i = 0; i < request_body.length; i++ )
                    dos.flush(); // Including this line may produce
                    // undesiredresults on certain devices
                }

                // obtain DataInputStream for receiving server response
                dis = new DataInputStream(hcon.openInputStream());
                // retrieve the response from server
                int ch;
                while ((ch = dis.read()) != -1) {
                    responseMessage.append((char) ch);
                }// end while( ( ch = dis.read() ) != -1 ) {
                // check status code
                int status = hcon.getResponseCode();
                switch (status) {
                case HttpConnection.HTTP_OK: // Success!
                    break;
                case HttpConnection.HTTP_TEMP_REDIRECT:
                case HttpConnection.HTTP_MOVED_TEMP:
                case HttpConnection.HTTP_MOVED_PERM:
                    // Redirect: get the new location
                    url = hcon.getHeaderField("location");
                    System.out.println("Redirect: " + url);

                    if (dis != null) dis.close();
                    if (hcon != null) hcon.close();
                    hcon = null;
                    redirectTimes++;
                    redirect = true;
                    break;
                default:
                    // Error: throw exception
                    hcon.close();
                    throw new IOException("Response status not OK:" + status);
                }

                // max 5 redirects
            } while (redirect == true && redirectTimes < 5);

            if (redirectTimes == 5) {
                throw new IOException("Too much redirects");
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.append("ERROR");
        } finally {
            // free up i/o streams and http connection
            try {
                if (hcon != null) hcon.close();
                if (dis != null) dis.close();
                if (dos != null) dos.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }// end try/catch
        }// end try/catch/finally
        return responseMessage.toString();
    }
}
