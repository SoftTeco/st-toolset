package com.softteco.toolset.push.android;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author sergeizenevich
 */
public class RequestDto implements Serializable {

    public List<String> registration_ids;
    /**
     * This parameter specifies the key-value pairs of the message's payload. There is no limit on the number of key-value pairs, but there is a total message size limit of 4kb.
     * 
     * For instance, in Android, data:{"score":"3x1"} would result in an intent extra named score with the string value 3x1.
     * 
     * The key should not be a reserved word (from or any word starting with google). It is also not recommended to use words defined in this table (such as collapse_key) because that could yield unpredictable outcomes. 
     * 
     * Values in string types are recommended. You have to convert values in objects or other non-string data types (e.g., integers or booleans) to string.
     */
    public Object data;
    /**
     * This parameters identifies a group of messages (e.g., with collapse_key: "Updates Available") that can be collapsed, so that only the last message gets sent when delivery can be resumed. This is intended to avoid sending too many of the same messages when the device comes back online or becomes active (see delay_while_idle).
     * 
     * Note that there is no guarantee of the order in which messages get sent.
     * 
     * Messages with collapse key are also called send-to-sync messages messages.
     * 
     * Note: A maximum of 4 different collapse keys is allowed at any given time. This means a GCM connection server can simultaneously store 4 different send-to-sync messages per client app. If you exceed this number, there is no guarantee which 4 collapse keys the GCM connection server will keep.
     */
    public String collapse_key;
    /**
     * This parameter specifies how long (in seconds) the message should be kept in GCM storage if the device is offline. The maximum time to live supported is 4 weeks.
     * 
     * The default value is 4 weeks.
     */
    public Integer time_to_live;
    /**
     * When this parameter is set to true, it indicates that the message should not be sent until the device becomes active. 
     * 
     * The default value is false.
     */
    public Boolean delay_while_idle;
    /**
     * This parameter, when set to true, allows developers to test a request without actually sending a message.
     * 
     * The default value is false.
     */
    public Boolean dry_run;
    /**
     * This parameter lets 3rd-party app server request confirmation of message delivery.
     * 
     * When this parameter is set to true, CCS sends a delivery receipt when the device confirms that it received the message.
     * 
     * The default value is false.
     */
    public Boolean delivery_receipt_requested;
}
