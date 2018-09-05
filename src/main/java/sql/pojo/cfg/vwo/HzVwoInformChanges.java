package sql.pojo.cfg.vwo;

/**
 * 通知关联人员
 */
public class HzVwoInformChanges {
    /**
     * 主键
     */
    private Long id;
    /**
     * vwo_主键，作为外键
     */
    private Long vwoId;
    /**
     * vwo号
     */
    private String vwoNum;
    /**
     * 关联人员ID
     */
    private Long personId;
    /**
     * 关联人员人员名称
     */
    private String personName;
    /**
     * 关联人员的部门ID
     */
    private Long personDeptId;
    /**
     * 关联人员的部门名称
     */
    private String personDeptName;
    /**
     * 关联零件号
     */
    private String partId;
    /**
     * 关联零件名称
     */
    private String partName;
    /**
     * 关联的零件PUID，数据库中主键，当作外键
     */
    private String partPuid;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVwoId() {
        return vwoId;
    }

    public void setVwoId(Long vwoId) {
        this.vwoId = vwoId;
    }

    public String getVwoNum() {
        return vwoNum;
    }

    public void setVwoNum(String vwoNum) {
        this.vwoNum = vwoNum == null ? null : vwoNum.trim();
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Long getPersonDeptId() {
        return personDeptId;
    }

    public void setPersonDeptId(Long personDeptId) {
        this.personDeptId = personDeptId;
    }

    public String getPersonDeptName() {
        return personDeptName;
    }

    public void setPersonDeptName(String personDeptName) {
        this.personDeptName = personDeptName;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPartPuid() {
        return partPuid;
    }

    public void setPartPuid(String partPuid) {
        this.partPuid = partPuid == null ? null : partPuid.trim();
    }

}