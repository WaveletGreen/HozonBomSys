
package webservice.base.cfg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 由TC系统将以下字段传输至SAP系统，SAP接口执行特性及特性值的创建或修改，SAP接口接收到数据后首先进行数据校验，若发现数据有错误则返回错误消息，若数据没有错误则执行创建或更改，执行完成后将消息返回给TC系统。
 * TC系统传输给SAP系统的数据要求：
 * 	TC系统修改某特性下的特性值时，需要传输两条数据，标识为D（删除）和A（添加）；
 * 	TC系统新增一个特性值时，传输一条数据，标识为A（添加）；
 * 	TC系统删除某特性下的某特性值时（不是指失效），传输一条数据时，标识为D（删除）；
 * 	TC系统更新某特性或特性值描述时，传输一条数据，标识为U。
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZPP_TCI002", propOrder = {"zpackno", "zitem", "zactionid", "zatnam", "zatbez", "zatwrt", "zatwtb",
        "reserve01", "reserve02", "reserve03", "reserve04", "reserve05"})
public class ZPPTCI002 implements Cloneable{
    /**
     * 数据包号
     */
    @XmlElement(name = "ZPACKNO", required = true)
    protected String zpackno;
    /**
     * 行号
     */
    @XmlElement(name = "ZITEM", required = true)
    protected String zitem;
    /**
     * 动作描述代码
     */
    @XmlElement(name = "ZACTIONID", required = true)
    protected String zactionid;
    /**
     * 特性编码
     */
    @XmlElement(name = "ZATNAM", required = true)
    protected String zatnam;
    /**
     * 特性描述
     */
    @XmlElement(name = "ZATBEZ", required = true)
    protected String zatbez;
    /**
     * 特性值编码
     */
    @XmlElement(name = "ZATWRT", required = true)
    protected String zatwrt;
    /**
     * 特性值描述
     */
    @XmlElement(name = "ZATWTB", required = true)
    protected String zatwtb;
    /**
     * 预留字段
     */
    @XmlElement(name = "RESERVE01", required = true)
    protected String reserve01;
    /**
     * 预留字段
     */
    @XmlElement(name = "RESERVE02", required = true)
    protected String reserve02;
    /**
     * 预留字段
     */
    @XmlElement(name = "RESERVE03", required = true)
    protected String reserve03;
    /**
     * 预留字段
     */
    @XmlElement(name = "RESERVE04", required = true)
    protected String reserve04;
    /**
     * 预留字段
     */
    @XmlElement(name = "RESERVE05", required = true)
    protected String reserve05;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Gets the value of the zpackno property.
     *
     * @return possible object is {@link String }
     */
    public String getZPACKNO() {
        return zpackno;
    }

    /**
     * Sets the value of the zpackno property.
     *
     * @param value allowed object is {@link String }
     */
    public void setZPACKNO(String value) {
        this.zpackno = value;
    }

    /**
     * Gets the value of the zitem property.
     *
     * @return possible object is {@link String }
     */
    public String getZITEM() {
        return zitem;
    }

    /**
     * Sets the value of the zitem property.
     *
     * @param value allowed object is {@link String }
     */
    public void setZITEM(String value) {
        this.zitem = value;
    }

    /**
     * Gets the value of the zactionid property.
     *
     * @return possible object is {@link String }
     */
    public String getZACTIONID() {
        return zactionid;
    }

    /**
     * Sets the value of the zactionid property.
     *
     * @param value allowed object is {@link String }
     */
    public void setZACTIONID(String value) {
        this.zactionid = value;
    }

    /**
     * Gets the value of the zatnam property.
     *
     * @return possible object is {@link String }
     */
    public String getZATNAM() {
        return zatnam;
    }

    /**
     * Sets the value of the zatnam property.
     *
     * @param value allowed object is {@link String }
     */
    public void setZATNAM(String value) {
        this.zatnam = value;
    }

    /**
     * Gets the value of the zatbez property.
     *
     * @return possible object is {@link String }
     */
    public String getZATBEZ() {
        return zatbez;
    }

    /**
     * Sets the value of the zatbez property.
     *
     * @param value allowed object is {@link String }
     */
    public void setZATBEZ(String value) {
        this.zatbez = value;
    }

    /**
     * Gets the value of the zatwrt property.
     *
     * @return possible object is {@link String }
     */
    public String getZATWRT() {
        return zatwrt;
    }

    /**
     * Sets the value of the zatwrt property.
     *
     * @param value allowed object is {@link String }
     */
    public void setZATWRT(String value) {
        this.zatwrt = value;
    }

    /**
     * Gets the value of the zatwtb property.
     *
     * @return possible object is {@link String }
     */
    public String getZATWTB() {
        return zatwtb;
    }

    /**
     * Sets the value of the zatwtb property.
     *
     * @param value allowed object is {@link String }
     */
    public void setZATWTB(String value) {
        this.zatwtb = value;
    }

    /**
     * Gets the value of the reserve01 property.
     *
     * @return possible object is {@link String }
     */
    public String getRESERVE01() {
        return reserve01;
    }

    /**
     * Sets the value of the reserve01 property.
     *
     * @param value allowed object is {@link String }
     */
    public void setRESERVE01(String value) {
        this.reserve01 = value;
    }

    /**
     * Gets the value of the reserve02 property.
     *
     * @return possible object is {@link String }
     */
    public String getRESERVE02() {
        return reserve02;
    }

    /**
     * Sets the value of the reserve02 property.
     *
     * @param value allowed object is {@link String }
     */
    public void setRESERVE02(String value) {
        this.reserve02 = value;
    }

    /**
     * Gets the value of the reserve03 property.
     *
     * @return possible object is {@link String }
     */
    public String getRESERVE03() {
        return reserve03;
    }

    /**
     * Sets the value of the reserve03 property.
     *
     * @param value allowed object is {@link String }
     */
    public void setRESERVE03(String value) {
        this.reserve03 = value;
    }

    /**
     * Gets the value of the reserve04 property.
     *
     * @return possible object is {@link String }
     */
    public String getRESERVE04() {
        return reserve04;
    }

    /**
     * Sets the value of the reserve04 property.
     *
     * @param value allowed object is {@link String }
     */
    public void setRESERVE04(String value) {
        this.reserve04 = value;
    }

    /**
     * Gets the value of the reserve05 property.
     *
     * @return possible object is {@link String }
     */
    public String getRESERVE05() {
        return reserve05;
    }

    /**
     * Sets the value of the reserve05 property.
     *
     * @param value allowed object is {@link String }
     */
    public void setRESERVE05(String value) {
        this.reserve05 = value;
    }

}
