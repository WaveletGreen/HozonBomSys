
package webservice.base.classify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 由TC系统将以下字段传输至SAP系统，SAP接口执行分类、配置参数文件的创建，并将特性分配至创建的分类下，将分类分配给可配置物料；
 * SAP接口接收到数据后首先进行数据校验，若发现数据有错误则返回错误消息，若数据没有错误则执行创建或更改，执行完成后将消息返回给TC系统。
 * TC系统传输给SAP系统的数据要求：由于配置值在整车中可能已经被使用，不能进行删除，
 * <p>
 * 1.	由TC系统控制是否可以传输删除数据，若能删除某特性，则传输D标识；
 * 2.	可配置物料下增加特性时TC系统标识为A；
 * 3.	TC系统做生效或失效处理时，不传输数据；
 * <p>
 * SAP处理：SAP系统接收到传输数据后，首先根据可配置物料号在系统中查找是否有分类数据；
 * 1.	若不能找到分类数据，则以可配置物料编码命名创建分类数据，并将特性分配给此分类、分类分配给可配置物料，同时以可配置物料编码命名创建配置参数文件；
 * 2.	若能找到分类数据且存在配置参数文件，则直接在找到的分类数据下添加标识为A的特性；
 * 3.	若能找到分类数据且不存在配置参数文件，则在分类数据下添加标识为A的特性后以可配置物料编码命名创建配置参数文件。
 * SAP系统将通过集成进行如下处理：
 * 1、	维护分类
 * 2、	维护衍生物料特性值分配
 * 3、	维护可配置物料（超级物料）配置参数文件
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZPP_TCI003", propOrder = {"zpackno", "zitem", "zactionid", "zmatnr", "zatnam", "zatwrt", "zclass",
        "reserve01", "reserve02", "reserve03", "reserve04", "reserve05"})
public class ZPPTCI003 implements Cloneable{

    /**
     * 数据包号
     */
    @XmlElement(name = "ZPACKNO", required = true)
    protected String zpackno;
    /**
     * 超级物料
     */
    @XmlElement(name = "ZITEM", required = true)
    protected String zitem;
    /**
     * 动作描述代码
     */
    @XmlElement(name = "ZACTIONID", required = true)
    protected String zactionid;
    /**
     * 可配置物料编码
     */
    @XmlElement(name = "ZMATNR", required = true)
    protected String zmatnr;
    /**
     * 特性编码
     */
    @XmlElement(name = "ZATNAM", required = true)
    protected String zatnam;
    /**
     * 特性值编码
     */
    @XmlElement(name = "ZATWRT", required = true)
    protected String zatwrt;
    /**
     * 模型族-分类
     */
    @XmlElement(name = "ZCLASS", required = true)
    protected String zclass;
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
     * Gets the value of the zmatnr property.
     *
     * @return possible object is {@link String }
     */
    public String getZMATNR() {
        return zmatnr;
    }

    /**
     * Sets the value of the zmatnr property.
     *
     * @param value allowed object is {@link String }
     */
    public void setZMATNR(String value) {
        this.zmatnr = value;
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
     * Gets the value of the zclass property.
     *
     * @return possible object is {@link String }
     */
    public String getZCLASS() {
        return zclass;
    }

    /**
     * Sets the value of the zclass property.
     *
     * @param value allowed object is {@link String }
     */
    public void setZCLASS(String value) {
        this.zclass = value;
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
