package newsemc.com.awit.news.newsemcapp.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import newsemc.com.awit.news.newsemcapp.dao.ImageInfo;
import newsemc.com.awit.news.newsemcapp.dao.OASysInfo;
import newsemc.com.awit.news.newsemcapp.dao.DetailInfo;
import newsemc.com.awit.news.newsemcapp.dao.ProjectInfo;
import newsemc.com.awit.news.newsemcapp.dao.ProjectTtrendsInfo;
import newsemc.com.awit.news.newsemcapp.dao.MeasureDetailInfo;
import newsemc.com.awit.news.newsemcapp.dao.DealInfo;
import newsemc.com.awit.news.newsemcapp.dao.PlanInfo;
import newsemc.com.awit.news.newsemcapp.dao.ProblemInfo;
import newsemc.com.awit.news.newsemcapp.dao.PreceptInfo;
import newsemc.com.awit.news.newsemcapp.dao.WorkpieceInfo;
import newsemc.com.awit.news.newsemcapp.dao.ScienceInfo;
import newsemc.com.awit.news.newsemcapp.dao.AuditInfo;
import newsemc.com.awit.news.newsemcapp.dao.CraftInfo;
import newsemc.com.awit.news.newsemcapp.dao.SedimentationInfo;
import newsemc.com.awit.news.newsemcapp.dao.ProjectImpsInfo;
import newsemc.com.awit.news.newsemcapp.dao.LxlBxoInfo;
import newsemc.com.awit.news.newsemcapp.dao.DiaoDuInfo;
import newsemc.com.awit.news.newsemcapp.dao.LabInfo;
import newsemc.com.awit.news.newsemcapp.dao.BhzInfo;
import newsemc.com.awit.news.newsemcapp.dao.ZdgcInfo;
import newsemc.com.awit.news.newsemcapp.dao.PersonInfo;
import newsemc.com.awit.news.newsemcapp.dao.DepartmentInfo;
import newsemc.com.awit.news.newsemcapp.dao.LoginInfo;
import newsemc.com.awit.news.newsemcapp.dao.DepartsInfo;
import newsemc.com.awit.news.newsemcapp.dao.SsidInfo;
import newsemc.com.awit.news.newsemcapp.dao.DeptsInfo;
import newsemc.com.awit.news.newsemcapp.dao.CompanyInfo;
import newsemc.com.awit.news.newsemcapp.dao.IconInfo;
import newsemc.com.awit.news.newsemcapp.dao.DanDianLoginPub;
import newsemc.com.awit.news.newsemcapp.dao.AlterPwdInfo;
import newsemc.com.awit.news.newsemcapp.dao.PushInfo;
import newsemc.com.awit.news.newsemcapp.dao.ApkUpdateInfo;
import newsemc.com.awit.news.newsemcapp.dao.SpecialAccountInfo;
import newsemc.com.awit.news.newsemcapp.dao.CompanyLogoInfo;
import newsemc.com.awit.news.newsemcapp.dao.FirstMenuInfo;
import newsemc.com.awit.news.newsemcapp.dao.SecondMenuInfo;
import newsemc.com.awit.news.newsemcapp.dao.ProjectInfoDetail;
import newsemc.com.awit.news.newsemcapp.dao.ProjectInfoDetailPic;
import newsemc.com.awit.news.newsemcapp.dao.ProjectInfoDetailPeriod;
import newsemc.com.awit.news.newsemcapp.dao.ProjectInfoDetailMainItem;

import newsemc.com.awit.news.newsemcapp.dao.ImageInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.OASysInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.DetailInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.ProjectInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.ProjectTtrendsInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.MeasureDetailInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.DealInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.PlanInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.ProblemInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.PreceptInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.WorkpieceInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.ScienceInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.AuditInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.CraftInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.SedimentationInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.ProjectImpsInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.LxlBxoInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.DiaoDuInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.LabInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.BhzInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.ZdgcInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.PersonInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.DepartmentInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.LoginInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.DepartsInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.SsidInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.DeptsInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.CompanyInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.IconInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.DanDianLoginPubDao;
import newsemc.com.awit.news.newsemcapp.dao.AlterPwdInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.PushInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.ApkUpdateInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.SpecialAccountInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.CompanyLogoInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.FirstMenuInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.SecondMenuInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.ProjectInfoDetailDao;
import newsemc.com.awit.news.newsemcapp.dao.ProjectInfoDetailPicDao;
import newsemc.com.awit.news.newsemcapp.dao.ProjectInfoDetailPeriodDao;
import newsemc.com.awit.news.newsemcapp.dao.ProjectInfoDetailMainItemDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig imageInfoDaoConfig;
    private final DaoConfig oASysInfoDaoConfig;
    private final DaoConfig detailInfoDaoConfig;
    private final DaoConfig projectInfoDaoConfig;
    private final DaoConfig projectTtrendsInfoDaoConfig;
    private final DaoConfig measureDetailInfoDaoConfig;
    private final DaoConfig dealInfoDaoConfig;
    private final DaoConfig planInfoDaoConfig;
    private final DaoConfig problemInfoDaoConfig;
    private final DaoConfig preceptInfoDaoConfig;
    private final DaoConfig workpieceInfoDaoConfig;
    private final DaoConfig scienceInfoDaoConfig;
    private final DaoConfig auditInfoDaoConfig;
    private final DaoConfig craftInfoDaoConfig;
    private final DaoConfig sedimentationInfoDaoConfig;
    private final DaoConfig projectImpsInfoDaoConfig;
    private final DaoConfig lxlBxoInfoDaoConfig;
    private final DaoConfig diaoDuInfoDaoConfig;
    private final DaoConfig labInfoDaoConfig;
    private final DaoConfig bhzInfoDaoConfig;
    private final DaoConfig zdgcInfoDaoConfig;
    private final DaoConfig personInfoDaoConfig;
    private final DaoConfig departmentInfoDaoConfig;
    private final DaoConfig loginInfoDaoConfig;
    private final DaoConfig departsInfoDaoConfig;
    private final DaoConfig ssidInfoDaoConfig;
    private final DaoConfig deptsInfoDaoConfig;
    private final DaoConfig companyInfoDaoConfig;
    private final DaoConfig iconInfoDaoConfig;
    private final DaoConfig danDianLoginPubDaoConfig;
    private final DaoConfig alterPwdInfoDaoConfig;
    private final DaoConfig pushInfoDaoConfig;
    private final DaoConfig apkUpdateInfoDaoConfig;
    private final DaoConfig specialAccountInfoDaoConfig;
    private final DaoConfig companyLogoInfoDaoConfig;
    private final DaoConfig firstMenuInfoDaoConfig;
    private final DaoConfig secondMenuInfoDaoConfig;
    private final DaoConfig projectInfoDetailDaoConfig;
    private final DaoConfig projectInfoDetailPicDaoConfig;
    private final DaoConfig projectInfoDetailPeriodDaoConfig;
    private final DaoConfig projectInfoDetailMainItemDaoConfig;

    private final ImageInfoDao imageInfoDao;
    private final OASysInfoDao oASysInfoDao;
    private final DetailInfoDao detailInfoDao;
    private final ProjectInfoDao projectInfoDao;
    private final ProjectTtrendsInfoDao projectTtrendsInfoDao;
    private final MeasureDetailInfoDao measureDetailInfoDao;
    private final DealInfoDao dealInfoDao;
    private final PlanInfoDao planInfoDao;
    private final ProblemInfoDao problemInfoDao;
    private final PreceptInfoDao preceptInfoDao;
    private final WorkpieceInfoDao workpieceInfoDao;
    private final ScienceInfoDao scienceInfoDao;
    private final AuditInfoDao auditInfoDao;
    private final CraftInfoDao craftInfoDao;
    private final SedimentationInfoDao sedimentationInfoDao;
    private final ProjectImpsInfoDao projectImpsInfoDao;
    private final LxlBxoInfoDao lxlBxoInfoDao;
    private final DiaoDuInfoDao diaoDuInfoDao;
    private final LabInfoDao labInfoDao;
    private final BhzInfoDao bhzInfoDao;
    private final ZdgcInfoDao zdgcInfoDao;
    private final PersonInfoDao personInfoDao;
    private final DepartmentInfoDao departmentInfoDao;
    private final LoginInfoDao loginInfoDao;
    private final DepartsInfoDao departsInfoDao;
    private final SsidInfoDao ssidInfoDao;
    private final DeptsInfoDao deptsInfoDao;
    private final CompanyInfoDao companyInfoDao;
    private final IconInfoDao iconInfoDao;
    private final DanDianLoginPubDao danDianLoginPubDao;
    private final AlterPwdInfoDao alterPwdInfoDao;
    private final PushInfoDao pushInfoDao;
    private final ApkUpdateInfoDao apkUpdateInfoDao;
    private final SpecialAccountInfoDao specialAccountInfoDao;
    private final CompanyLogoInfoDao companyLogoInfoDao;
    private final FirstMenuInfoDao firstMenuInfoDao;
    private final SecondMenuInfoDao secondMenuInfoDao;
    private final ProjectInfoDetailDao projectInfoDetailDao;
    private final ProjectInfoDetailPicDao projectInfoDetailPicDao;
    private final ProjectInfoDetailPeriodDao projectInfoDetailPeriodDao;
    private final ProjectInfoDetailMainItemDao projectInfoDetailMainItemDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        imageInfoDaoConfig = daoConfigMap.get(ImageInfoDao.class).clone();
        imageInfoDaoConfig.initIdentityScope(type);

        oASysInfoDaoConfig = daoConfigMap.get(OASysInfoDao.class).clone();
        oASysInfoDaoConfig.initIdentityScope(type);

        detailInfoDaoConfig = daoConfigMap.get(DetailInfoDao.class).clone();
        detailInfoDaoConfig.initIdentityScope(type);

        projectInfoDaoConfig = daoConfigMap.get(ProjectInfoDao.class).clone();
        projectInfoDaoConfig.initIdentityScope(type);

        projectTtrendsInfoDaoConfig = daoConfigMap.get(ProjectTtrendsInfoDao.class).clone();
        projectTtrendsInfoDaoConfig.initIdentityScope(type);

        measureDetailInfoDaoConfig = daoConfigMap.get(MeasureDetailInfoDao.class).clone();
        measureDetailInfoDaoConfig.initIdentityScope(type);

        dealInfoDaoConfig = daoConfigMap.get(DealInfoDao.class).clone();
        dealInfoDaoConfig.initIdentityScope(type);

        planInfoDaoConfig = daoConfigMap.get(PlanInfoDao.class).clone();
        planInfoDaoConfig.initIdentityScope(type);

        problemInfoDaoConfig = daoConfigMap.get(ProblemInfoDao.class).clone();
        problemInfoDaoConfig.initIdentityScope(type);

        preceptInfoDaoConfig = daoConfigMap.get(PreceptInfoDao.class).clone();
        preceptInfoDaoConfig.initIdentityScope(type);

        workpieceInfoDaoConfig = daoConfigMap.get(WorkpieceInfoDao.class).clone();
        workpieceInfoDaoConfig.initIdentityScope(type);

        scienceInfoDaoConfig = daoConfigMap.get(ScienceInfoDao.class).clone();
        scienceInfoDaoConfig.initIdentityScope(type);

        auditInfoDaoConfig = daoConfigMap.get(AuditInfoDao.class).clone();
        auditInfoDaoConfig.initIdentityScope(type);

        craftInfoDaoConfig = daoConfigMap.get(CraftInfoDao.class).clone();
        craftInfoDaoConfig.initIdentityScope(type);

        sedimentationInfoDaoConfig = daoConfigMap.get(SedimentationInfoDao.class).clone();
        sedimentationInfoDaoConfig.initIdentityScope(type);

        projectImpsInfoDaoConfig = daoConfigMap.get(ProjectImpsInfoDao.class).clone();
        projectImpsInfoDaoConfig.initIdentityScope(type);

        lxlBxoInfoDaoConfig = daoConfigMap.get(LxlBxoInfoDao.class).clone();
        lxlBxoInfoDaoConfig.initIdentityScope(type);

        diaoDuInfoDaoConfig = daoConfigMap.get(DiaoDuInfoDao.class).clone();
        diaoDuInfoDaoConfig.initIdentityScope(type);

        labInfoDaoConfig = daoConfigMap.get(LabInfoDao.class).clone();
        labInfoDaoConfig.initIdentityScope(type);

        bhzInfoDaoConfig = daoConfigMap.get(BhzInfoDao.class).clone();
        bhzInfoDaoConfig.initIdentityScope(type);

        zdgcInfoDaoConfig = daoConfigMap.get(ZdgcInfoDao.class).clone();
        zdgcInfoDaoConfig.initIdentityScope(type);

        personInfoDaoConfig = daoConfigMap.get(PersonInfoDao.class).clone();
        personInfoDaoConfig.initIdentityScope(type);

        departmentInfoDaoConfig = daoConfigMap.get(DepartmentInfoDao.class).clone();
        departmentInfoDaoConfig.initIdentityScope(type);

        loginInfoDaoConfig = daoConfigMap.get(LoginInfoDao.class).clone();
        loginInfoDaoConfig.initIdentityScope(type);

        departsInfoDaoConfig = daoConfigMap.get(DepartsInfoDao.class).clone();
        departsInfoDaoConfig.initIdentityScope(type);

        ssidInfoDaoConfig = daoConfigMap.get(SsidInfoDao.class).clone();
        ssidInfoDaoConfig.initIdentityScope(type);

        deptsInfoDaoConfig = daoConfigMap.get(DeptsInfoDao.class).clone();
        deptsInfoDaoConfig.initIdentityScope(type);

        companyInfoDaoConfig = daoConfigMap.get(CompanyInfoDao.class).clone();
        companyInfoDaoConfig.initIdentityScope(type);

        iconInfoDaoConfig = daoConfigMap.get(IconInfoDao.class).clone();
        iconInfoDaoConfig.initIdentityScope(type);

        danDianLoginPubDaoConfig = daoConfigMap.get(DanDianLoginPubDao.class).clone();
        danDianLoginPubDaoConfig.initIdentityScope(type);

        alterPwdInfoDaoConfig = daoConfigMap.get(AlterPwdInfoDao.class).clone();
        alterPwdInfoDaoConfig.initIdentityScope(type);

        pushInfoDaoConfig = daoConfigMap.get(PushInfoDao.class).clone();
        pushInfoDaoConfig.initIdentityScope(type);

        apkUpdateInfoDaoConfig = daoConfigMap.get(ApkUpdateInfoDao.class).clone();
        apkUpdateInfoDaoConfig.initIdentityScope(type);

        specialAccountInfoDaoConfig = daoConfigMap.get(SpecialAccountInfoDao.class).clone();
        specialAccountInfoDaoConfig.initIdentityScope(type);

        companyLogoInfoDaoConfig = daoConfigMap.get(CompanyLogoInfoDao.class).clone();
        companyLogoInfoDaoConfig.initIdentityScope(type);

        firstMenuInfoDaoConfig = daoConfigMap.get(FirstMenuInfoDao.class).clone();
        firstMenuInfoDaoConfig.initIdentityScope(type);

        secondMenuInfoDaoConfig = daoConfigMap.get(SecondMenuInfoDao.class).clone();
        secondMenuInfoDaoConfig.initIdentityScope(type);

        projectInfoDetailDaoConfig = daoConfigMap.get(ProjectInfoDetailDao.class).clone();
        projectInfoDetailDaoConfig.initIdentityScope(type);

        projectInfoDetailPicDaoConfig = daoConfigMap.get(ProjectInfoDetailPicDao.class).clone();
        projectInfoDetailPicDaoConfig.initIdentityScope(type);

        projectInfoDetailPeriodDaoConfig = daoConfigMap.get(ProjectInfoDetailPeriodDao.class).clone();
        projectInfoDetailPeriodDaoConfig.initIdentityScope(type);

        projectInfoDetailMainItemDaoConfig = daoConfigMap.get(ProjectInfoDetailMainItemDao.class).clone();
        projectInfoDetailMainItemDaoConfig.initIdentityScope(type);

        imageInfoDao = new ImageInfoDao(imageInfoDaoConfig, this);
        oASysInfoDao = new OASysInfoDao(oASysInfoDaoConfig, this);
        detailInfoDao = new DetailInfoDao(detailInfoDaoConfig, this);
        projectInfoDao = new ProjectInfoDao(projectInfoDaoConfig, this);
        projectTtrendsInfoDao = new ProjectTtrendsInfoDao(projectTtrendsInfoDaoConfig, this);
        measureDetailInfoDao = new MeasureDetailInfoDao(measureDetailInfoDaoConfig, this);
        dealInfoDao = new DealInfoDao(dealInfoDaoConfig, this);
        planInfoDao = new PlanInfoDao(planInfoDaoConfig, this);
        problemInfoDao = new ProblemInfoDao(problemInfoDaoConfig, this);
        preceptInfoDao = new PreceptInfoDao(preceptInfoDaoConfig, this);
        workpieceInfoDao = new WorkpieceInfoDao(workpieceInfoDaoConfig, this);
        scienceInfoDao = new ScienceInfoDao(scienceInfoDaoConfig, this);
        auditInfoDao = new AuditInfoDao(auditInfoDaoConfig, this);
        craftInfoDao = new CraftInfoDao(craftInfoDaoConfig, this);
        sedimentationInfoDao = new SedimentationInfoDao(sedimentationInfoDaoConfig, this);
        projectImpsInfoDao = new ProjectImpsInfoDao(projectImpsInfoDaoConfig, this);
        lxlBxoInfoDao = new LxlBxoInfoDao(lxlBxoInfoDaoConfig, this);
        diaoDuInfoDao = new DiaoDuInfoDao(diaoDuInfoDaoConfig, this);
        labInfoDao = new LabInfoDao(labInfoDaoConfig, this);
        bhzInfoDao = new BhzInfoDao(bhzInfoDaoConfig, this);
        zdgcInfoDao = new ZdgcInfoDao(zdgcInfoDaoConfig, this);
        personInfoDao = new PersonInfoDao(personInfoDaoConfig, this);
        departmentInfoDao = new DepartmentInfoDao(departmentInfoDaoConfig, this);
        loginInfoDao = new LoginInfoDao(loginInfoDaoConfig, this);
        departsInfoDao = new DepartsInfoDao(departsInfoDaoConfig, this);
        ssidInfoDao = new SsidInfoDao(ssidInfoDaoConfig, this);
        deptsInfoDao = new DeptsInfoDao(deptsInfoDaoConfig, this);
        companyInfoDao = new CompanyInfoDao(companyInfoDaoConfig, this);
        iconInfoDao = new IconInfoDao(iconInfoDaoConfig, this);
        danDianLoginPubDao = new DanDianLoginPubDao(danDianLoginPubDaoConfig, this);
        alterPwdInfoDao = new AlterPwdInfoDao(alterPwdInfoDaoConfig, this);
        pushInfoDao = new PushInfoDao(pushInfoDaoConfig, this);
        apkUpdateInfoDao = new ApkUpdateInfoDao(apkUpdateInfoDaoConfig, this);
        specialAccountInfoDao = new SpecialAccountInfoDao(specialAccountInfoDaoConfig, this);
        companyLogoInfoDao = new CompanyLogoInfoDao(companyLogoInfoDaoConfig, this);
        firstMenuInfoDao = new FirstMenuInfoDao(firstMenuInfoDaoConfig, this);
        secondMenuInfoDao = new SecondMenuInfoDao(secondMenuInfoDaoConfig, this);
        projectInfoDetailDao = new ProjectInfoDetailDao(projectInfoDetailDaoConfig, this);
        projectInfoDetailPicDao = new ProjectInfoDetailPicDao(projectInfoDetailPicDaoConfig, this);
        projectInfoDetailPeriodDao = new ProjectInfoDetailPeriodDao(projectInfoDetailPeriodDaoConfig, this);
        projectInfoDetailMainItemDao = new ProjectInfoDetailMainItemDao(projectInfoDetailMainItemDaoConfig, this);

        registerDao(ImageInfo.class, imageInfoDao);
        registerDao(OASysInfo.class, oASysInfoDao);
        registerDao(DetailInfo.class, detailInfoDao);
        registerDao(ProjectInfo.class, projectInfoDao);
        registerDao(ProjectTtrendsInfo.class, projectTtrendsInfoDao);
        registerDao(MeasureDetailInfo.class, measureDetailInfoDao);
        registerDao(DealInfo.class, dealInfoDao);
        registerDao(PlanInfo.class, planInfoDao);
        registerDao(ProblemInfo.class, problemInfoDao);
        registerDao(PreceptInfo.class, preceptInfoDao);
        registerDao(WorkpieceInfo.class, workpieceInfoDao);
        registerDao(ScienceInfo.class, scienceInfoDao);
        registerDao(AuditInfo.class, auditInfoDao);
        registerDao(CraftInfo.class, craftInfoDao);
        registerDao(SedimentationInfo.class, sedimentationInfoDao);
        registerDao(ProjectImpsInfo.class, projectImpsInfoDao);
        registerDao(LxlBxoInfo.class, lxlBxoInfoDao);
        registerDao(DiaoDuInfo.class, diaoDuInfoDao);
        registerDao(LabInfo.class, labInfoDao);
        registerDao(BhzInfo.class, bhzInfoDao);
        registerDao(ZdgcInfo.class, zdgcInfoDao);
        registerDao(PersonInfo.class, personInfoDao);
        registerDao(DepartmentInfo.class, departmentInfoDao);
        registerDao(LoginInfo.class, loginInfoDao);
        registerDao(DepartsInfo.class, departsInfoDao);
        registerDao(SsidInfo.class, ssidInfoDao);
        registerDao(DeptsInfo.class, deptsInfoDao);
        registerDao(CompanyInfo.class, companyInfoDao);
        registerDao(IconInfo.class, iconInfoDao);
        registerDao(DanDianLoginPub.class, danDianLoginPubDao);
        registerDao(AlterPwdInfo.class, alterPwdInfoDao);
        registerDao(PushInfo.class, pushInfoDao);
        registerDao(ApkUpdateInfo.class, apkUpdateInfoDao);
        registerDao(SpecialAccountInfo.class, specialAccountInfoDao);
        registerDao(CompanyLogoInfo.class, companyLogoInfoDao);
        registerDao(FirstMenuInfo.class, firstMenuInfoDao);
        registerDao(SecondMenuInfo.class, secondMenuInfoDao);
        registerDao(ProjectInfoDetail.class, projectInfoDetailDao);
        registerDao(ProjectInfoDetailPic.class, projectInfoDetailPicDao);
        registerDao(ProjectInfoDetailPeriod.class, projectInfoDetailPeriodDao);
        registerDao(ProjectInfoDetailMainItem.class, projectInfoDetailMainItemDao);
    }
    
    public void clear() {
        imageInfoDaoConfig.getIdentityScope().clear();
        oASysInfoDaoConfig.getIdentityScope().clear();
        detailInfoDaoConfig.getIdentityScope().clear();
        projectInfoDaoConfig.getIdentityScope().clear();
        projectTtrendsInfoDaoConfig.getIdentityScope().clear();
        measureDetailInfoDaoConfig.getIdentityScope().clear();
        dealInfoDaoConfig.getIdentityScope().clear();
        planInfoDaoConfig.getIdentityScope().clear();
        problemInfoDaoConfig.getIdentityScope().clear();
        preceptInfoDaoConfig.getIdentityScope().clear();
        workpieceInfoDaoConfig.getIdentityScope().clear();
        scienceInfoDaoConfig.getIdentityScope().clear();
        auditInfoDaoConfig.getIdentityScope().clear();
        craftInfoDaoConfig.getIdentityScope().clear();
        sedimentationInfoDaoConfig.getIdentityScope().clear();
        projectImpsInfoDaoConfig.getIdentityScope().clear();
        lxlBxoInfoDaoConfig.getIdentityScope().clear();
        diaoDuInfoDaoConfig.getIdentityScope().clear();
        labInfoDaoConfig.getIdentityScope().clear();
        bhzInfoDaoConfig.getIdentityScope().clear();
        zdgcInfoDaoConfig.getIdentityScope().clear();
        personInfoDaoConfig.getIdentityScope().clear();
        departmentInfoDaoConfig.getIdentityScope().clear();
        loginInfoDaoConfig.getIdentityScope().clear();
        departsInfoDaoConfig.getIdentityScope().clear();
        ssidInfoDaoConfig.getIdentityScope().clear();
        deptsInfoDaoConfig.getIdentityScope().clear();
        companyInfoDaoConfig.getIdentityScope().clear();
        iconInfoDaoConfig.getIdentityScope().clear();
        danDianLoginPubDaoConfig.getIdentityScope().clear();
        alterPwdInfoDaoConfig.getIdentityScope().clear();
        pushInfoDaoConfig.getIdentityScope().clear();
        apkUpdateInfoDaoConfig.getIdentityScope().clear();
        specialAccountInfoDaoConfig.getIdentityScope().clear();
        companyLogoInfoDaoConfig.getIdentityScope().clear();
        firstMenuInfoDaoConfig.getIdentityScope().clear();
        secondMenuInfoDaoConfig.getIdentityScope().clear();
        projectInfoDetailDaoConfig.getIdentityScope().clear();
        projectInfoDetailPicDaoConfig.getIdentityScope().clear();
        projectInfoDetailPeriodDaoConfig.getIdentityScope().clear();
        projectInfoDetailMainItemDaoConfig.getIdentityScope().clear();
    }

    public ImageInfoDao getImageInfoDao() {
        return imageInfoDao;
    }

    public OASysInfoDao getOASysInfoDao() {
        return oASysInfoDao;
    }

    public DetailInfoDao getDetailInfoDao() {
        return detailInfoDao;
    }

    public ProjectInfoDao getProjectInfoDao() {
        return projectInfoDao;
    }

    public ProjectTtrendsInfoDao getProjectTtrendsInfoDao() {
        return projectTtrendsInfoDao;
    }

    public MeasureDetailInfoDao getMeasureDetailInfoDao() {
        return measureDetailInfoDao;
    }

    public DealInfoDao getDealInfoDao() {
        return dealInfoDao;
    }

    public PlanInfoDao getPlanInfoDao() {
        return planInfoDao;
    }

    public ProblemInfoDao getProblemInfoDao() {
        return problemInfoDao;
    }

    public PreceptInfoDao getPreceptInfoDao() {
        return preceptInfoDao;
    }

    public WorkpieceInfoDao getWorkpieceInfoDao() {
        return workpieceInfoDao;
    }

    public ScienceInfoDao getScienceInfoDao() {
        return scienceInfoDao;
    }

    public AuditInfoDao getAuditInfoDao() {
        return auditInfoDao;
    }

    public CraftInfoDao getCraftInfoDao() {
        return craftInfoDao;
    }

    public SedimentationInfoDao getSedimentationInfoDao() {
        return sedimentationInfoDao;
    }

    public ProjectImpsInfoDao getProjectImpsInfoDao() {
        return projectImpsInfoDao;
    }

    public LxlBxoInfoDao getLxlBxoInfoDao() {
        return lxlBxoInfoDao;
    }

    public DiaoDuInfoDao getDiaoDuInfoDao() {
        return diaoDuInfoDao;
    }

    public LabInfoDao getLabInfoDao() {
        return labInfoDao;
    }

    public BhzInfoDao getBhzInfoDao() {
        return bhzInfoDao;
    }

    public ZdgcInfoDao getZdgcInfoDao() {
        return zdgcInfoDao;
    }

    public PersonInfoDao getPersonInfoDao() {
        return personInfoDao;
    }

    public DepartmentInfoDao getDepartmentInfoDao() {
        return departmentInfoDao;
    }

    public LoginInfoDao getLoginInfoDao() {
        return loginInfoDao;
    }

    public DepartsInfoDao getDepartsInfoDao() {
        return departsInfoDao;
    }

    public SsidInfoDao getSsidInfoDao() {
        return ssidInfoDao;
    }

    public DeptsInfoDao getDeptsInfoDao() {
        return deptsInfoDao;
    }

    public CompanyInfoDao getCompanyInfoDao() {
        return companyInfoDao;
    }

    public IconInfoDao getIconInfoDao() {
        return iconInfoDao;
    }

    public DanDianLoginPubDao getDanDianLoginPubDao() {
        return danDianLoginPubDao;
    }

    public AlterPwdInfoDao getAlterPwdInfoDao() {
        return alterPwdInfoDao;
    }

    public PushInfoDao getPushInfoDao() {
        return pushInfoDao;
    }

    public ApkUpdateInfoDao getApkUpdateInfoDao() {
        return apkUpdateInfoDao;
    }

    public SpecialAccountInfoDao getSpecialAccountInfoDao() {
        return specialAccountInfoDao;
    }

    public CompanyLogoInfoDao getCompanyLogoInfoDao() {
        return companyLogoInfoDao;
    }

    public FirstMenuInfoDao getFirstMenuInfoDao() {
        return firstMenuInfoDao;
    }

    public SecondMenuInfoDao getSecondMenuInfoDao() {
        return secondMenuInfoDao;
    }

    public ProjectInfoDetailDao getProjectInfoDetailDao() {
        return projectInfoDetailDao;
    }

    public ProjectInfoDetailPicDao getProjectInfoDetailPicDao() {
        return projectInfoDetailPicDao;
    }

    public ProjectInfoDetailPeriodDao getProjectInfoDetailPeriodDao() {
        return projectInfoDetailPeriodDao;
    }

    public ProjectInfoDetailMainItemDao getProjectInfoDetailMainItemDao() {
        return projectInfoDetailMainItemDao;
    }

}
