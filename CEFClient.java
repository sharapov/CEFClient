import java.util.List;
import com.cloudbees.syslog.Facility;
import com.cloudbees.syslog.MessageFormat;
import com.cloudbees.syslog.Severity;
import com.cloudbees.syslog.sender.UdpSyslogMessageSender;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
 =============================================================================
 Generic use:
 =============================================================================
 CEFClient c = new CEFClient("YourName", "YourProduct");

 try
 {
 c.Port = Utils.ToNumber(YourNumberPort);
 c.SysLogServerIp = YourSysLogServerIp; 

 String user = "DonaldDuck";
 String message = "Login success";
 String action = "login";
 
 c.SendAsync(message, action, user, Level.Information, new List<Field>());

 }
 catch // (Exception ex)
 {
 //Console.WriteLine(ex);
 }

 =============================================================================
 Use for database tables:
 =============================================================================
 
 CEFClient c = new CEFClient("YourName", "YourProduct");

 try
 {
 c.Port = Utils.ToNumber(YourNumberPort);
 c.SysLogServerIp = YourSysLogServerIp; 

 List<SysLog.Field> fields = new List<SysLog.Field>()

 String user = "DonaldDuck";
 String message = "tPerson";
 String action = "insert";
 
 fields.Add(new F

ield() { FieldName = "Id", FieldValue = "1" });
 fields.Add(new Field() { FieldName = "SurName", FieldValue = "Smith" });
 fields.Add(new Field() { FieldName = "Name", FieldValue = "John" });
 
 c.SendAsync(message, action, user, Level.Warning, fields);

 }
 catch // (Exception ex)
 {
 //Console.WriteLine(ex);
 }

 */
public class CEFClient {

    //private static List<CEFField> Fields;
    //private IPHostEntry _ipHostInfo;
    //private IPAddress _ipAddress;
    //private IPEndPoint _ipLocalEndPoint;
    private UdpSyslogMessageSender messageSender;
    //private String _sysLogServerIp = null;
    private int _port = 2000;
    private String _vendor;
    private String _product;

    public CEFClient(String Vendor, String Product) {
        _vendor = Vendor;
        _product = Product;
        messageSender = new UdpSyslogMessageSender();
        //_ipHostInfo = //Dns.GetHostEntry(Dns.GetHostName());
        //_ipAddress = _ipHostInfo.AddressList[0];
        //_ipLocalEndPoint = new IPEndPoint(_ipAddress, 0); 
    }

    public int getPort() {
        return _port;
    }

    public void setPort(int _port) {
        this._port = _port;
    }

    private String SysLogServerIp;

    public String getSysLogServerIp() {
        return SysLogServerIp;
    }

    public void setSysLogServerIp(String SysLogServerIp) {
        this.SysLogServerIp = SysLogServerIp;
    }

    /// <summary>
    /// Send to SYSLOG server in async mode
    /// </summary>
    /// <param name="message">Free text</param>
    /// <param name="action">Action category(i.e. "login", "logout", "insert", "delete"...)</param>
    /// <param name="user">User</param>
    /// <param name="level">Severity</param>
    /// <param name="fields">Table record fields to log</param>
/*        public void SendAsync(String message, String action, String user, 
 Level level, List<Field> fields)
 {
 new Thread(() =>
 {
 sendAlg(message, action, user, level, fields);

 }).Start();
 }*/
    /// <summary> 
    /// Send to SYSLOG server in syncronous mode 
    /// </summary> 
    /// <param name="message">Free text</param> 
    /// <param name="action">Action category(i.e. "login", "logout", "insert", "delete"...)</param> 
    /// <param name="user">User</param>
    /// <param name="level">Severity</param>
    /// <param name="fields">Table record fields to log</param>
    /*public void Send(String message, String action, String user,
            Level level, List<Field> fields) {
        sendAlg(message, action, user, level, fields);
    }*/

 /*private void sendAlg(String message, String action, String user,
            Level level, List<Field> fields) {
        String msg = "";

        try {
            IPAddress serverAddr = IPAddress.Parse(_sysLogServerIp);
            IPEndPoint endPoint = new IPEndPoint(serverAddr, _port);

            List<CEFField> cefFields = new List<CEFField>();

            for (int i = 0; i < fields.Count; i++) {
                cefFields.Add(new CEFField(_vendor, _product, i, fields[i]));
            }

            CEFParser parser = new CEFParser(
                    "1.0", // Version = "1.0",
                    _ipHostInfo.HostName, // Host = ipHostInfo.HostName,
                    _vendor, // Vendor = "CompanyName",
                    _product, // Product = "ProductName",
                    action, // SignatureID = action,
                    message, // Text = message,
                    (int) level, // Severity = (int)level,
                    user, // UserName = user,
                    _ipAddress.ToString(), // SourceIp = ipAddress.ToString()
                    cefFields
            );

            msg = parser.CEFMessage;

            byte[] bytes = System.Text.Encoding.UTF8.GetBytes(msg);

            using(var client = new TcpClient()
            
                )
 /*{
 client.Connect(endPoint);

                if (client.Connected) {
                    using(NetworkStream stream = client.GetStream()
                    
                        )
 {
 stream.Write(bytes, 0, bytes.Length);
                        stream.Flush();
                        stream.Close();
                    }

                    client.Close();
                } else {
                    throw new Exception("Could not connect to server");
                }
            }
        } catch (Exception ex) {
            throw (ex);
        }
    }
}*/
    public enum Level {
        Test(0),
        Debug(1),
        Information(2),
        Notice(3),
        Warning(4),
        Suspect(5),
        Error(6),
        Critical(7),
        Alert(8),
        Emergency(9),
        Crash(10);
        private final int levelCode;

        private Level(int levelCode) {
            this.levelCode = levelCode;
        }

    }

    static public class CEFParser {

        private String _version;
        private String _host;
        private String _vendor;
        private String _product;
        private String _signatureID;
        private String _text;
        private int _severity;
        private String _userName;
        private String _sourceIp;
        private List<CEFField> _fields;

        public CEFParser(
                String Version,
                String Host,
                String Vendor,
                String Product,
                String SignatureId,
                String Text,
                int Severity,
                String Username,
                String SourceIp,
                List<CEFField> Fields
        ) {
            _version = Version;
            _host = Host;
            _vendor = Vendor;
            _product = Product;
            _signatureID = SignatureId;
            _text = Text;
            _severity = Severity;
            _userName = Username;
            _sourceIp = SourceIp;
            _fields = Fields;
        }

        public String getVersion() {
            return _version;
        }

        public void setVersion(String _version) {
            this._version = _version;
        }

        public String getHost() {
            return _host;
        }

        public void setHost(String _host) {
            this._host = _host;
        }

        public String getVendor() {
            return _vendor;
        }

        public void setVendor(String _vendor) {
            this._vendor = _vendor;
        }

        public String getProduct() {
            return _product;
        }

        public void setProduct(String _product) {
            this._product = _product;
        }

        public String getSignatureID() {
            return _signatureID;
        }

        public void setSignatureID(String _signatureID) {
            this._signatureID = _signatureID;
        }

        public String getText() {
            return _text;
        }

        public void setText(String _text) {
            this._text = _text;
        }

        public int getSeverity() {
            return _severity;
        }

        public void setSeverity(int _severity) {
            this._severity = _severity;
        }

        public String getUserName() {
            return _userName;
        }

        public void setUserName(String _userName) {
            this._userName = _userName;
        }

        public String getSourceIp() {
            return _sourceIp;
        }

        public void setSourceIp(String _sourceIp) {
            this._sourceIp = _sourceIp;
        }

        public List<CEFField> getFields() {
            return _fields;
        }

        public void setFields(List<CEFField> _fields) {
            this._fields = _fields;
        }


        /*public CEFParser(
            String Version,
            String Host,
            String Vendor,
            String Product,
            String SignatureId,
            String Text,
            int Severity,
            String Username,
            String SourceIp,
            List<CEFField> Fields) {
        _version = Version;
        _host = Host;
        _vendor = Vendor;
        _product = Product;
        _signatureID = SignatureId;
        _text = Text;
        _severity = Severity;
        _userName = Username;
        _sourceIp = SourceIp;
        _fields = Fields;
    }*/
        //private String Header;
        public String getHeader() {
            String formattedDate = new SimpleDateFormat("y-M-d H:m:s.S").format(new Date()); //parse("2017-9-11 13:1:28.9");
            //String formattedDate = DateTime.toString(); //Now.ToString("MMM dd HH:mm:ss", CultureInfo.CreateSpecificCulture("en-GB"));

            return String.format("%s %s", formattedDate, _host);
        }

        //private String Message;
        public String getMessage() {
            String ret = "CEF:0|"
                    + _vendor + "|"
                    + _product + "|"
                    + _version + "|"
                    + _signatureID + "|"
                    + _text + "|"
                    + _severity + "|"
                    + "src=" + _sourceIp + " "
                    + "suser=" + _userName;

            for (CEFField item : _fields) {
                ret += " " + item.getKey() + "=" + item.getValue();
            }

            ret += System.lineSeparator(); //Environment.NewLine;

            return ret;
        }

        //private String CEFMessage;
        public String getCEFMessage() {
            return getHeader() + " " + getMessage();
        }

    }

    static public class Field {

        private String FieldName; // { get; set; }
        private String FieldValue; //{ get; set; }

        public Field(String FieldName, String FieldValue) {
            super();
            this.FieldName = FieldName;
            this.FieldValue = FieldValue;
        }

        public String getFieldName() {
            return FieldName;
        }

        public void setFieldName(String FieldName) {
            this.FieldName = FieldName;
        }

        public String getFieldValue() {
            return FieldValue;
        }

        public void setFieldValue(String FieldValue) {
            this.FieldValue = FieldValue;
        }

    }

    public static class CEFField {

        private String _id;
        private Field _field;
        private String _vendor;
        private String _product;

        public CEFField(String Vendor, String Product, int Id, Field Field) {
            _vendor = Vendor;
            _product = Product;
            _id = String.format("%04d", Id); //Id.ToString().PadLeft(4, '0');
            _field = Field;
        }

        //private String key;
        public String getKey() {
            return _vendor + _product + _id + _field.FieldName;
        }

        //private String value;
        public String getValue() {
            return getNormalizeCEFMessage(_field.FieldValue);
        }

        private String getNormalizeCEFMessage(String message) {
            String ret = message.replace("|", "\\|");

            ret = ret.replace("\\", "\\\\");
            ret = ret.replace("=", "\\=");

            return ret;
        }

    }

    public static void main(String[] args) {
        UdpSyslogMessageSender messageSender = new UdpSyslogMessageSender();
        messageSender.setDefaultMessageHostname("myhostname"); // some syslog cloud services may use this field to transmit a secret key
        messageSender.setDefaultAppName("myapp");
        messageSender.setDefaultFacility(Facility.USER);
        messageSender.setDefaultSeverity(Severity.INFORMATIONAL);
        messageSender.setSyslogServerHostname("127.0.0.1");
// syslog udp usually uses port 514 as per https://tools.ietf.org/html/rfc3164#page-5
        messageSender.setSyslogServerPort(514);
        messageSender.setMessageFormat(MessageFormat.RFC_3164); // optional, default is RFC 3164

// send a Syslog message
//messageSender.sendMessage("This is a test message");        
        CEFClient c = new CEFClient("YourName", "YourProduct");
        c._port = 11;
        c.SysLogServerIp = "1.1.1.1";

        ArrayList<CEFClient.Field> fields = new ArrayList<>();

        String user = "DonaldDuck";
        String message = "tPerson";
        String action = "insert";

        fields.add(new CEFClient.Field("Id", "1"));
        fields.add(new CEFClient.Field("SurName", "Smith"));
        fields.add(new CEFClient.Field("Name", "John"));
        /*CEFClient.CEFParser parser = new CEFClient.CEFParser(
                    "1.0", // Version = "1.0",
                    _ipHostInfo.HostName, // Host = ipHostInfo.HostName,
                    _vendor, // Vendor = "CompanyName",
                    _product, // Product = "ProductName",
                    action, // SignatureID = action,
                    message, // Text = message,
                    (int) level, // Severity = (int)level,
                    user, // UserName = user,
                    _ipAddress.ToString(), // SourceIp = ipAddress.ToString()
                    cefFields
            );*/

        CEFClient.CEFParser cefParser;
        ArrayList<CEFClient.CEFField> cefFields = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            cefFields.add(new CEFClient.CEFField("ww", "eee", i, fields.get(i)));
        }
        cefParser = new CEFClient.CEFParser("version", "host", "vendor", "product", "signatureId", "text", 0, "username", "sourceIp", cefFields);
        String msg = cefParser.getCEFMessage();
        System.out.println(msg);
    }
}
