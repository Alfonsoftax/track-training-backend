package com.src.train.track.general.helper;
import static com.src.train.track.general.Constants.COMPANY;

import static com.src.train.track.general.utils.Safe.LOCALE_SPAIN;
import static org.apache.commons.lang3.StringUtils.removeStart;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.src.train.track.User.model.Usuario;
import com.src.train.track.general.GeneralData;
import com.src.train.track.general.domain.ProcessContext;
import com.src.train.track.general.domain.TenantContext;
import com.src.train.track.general.domain.TransactionType;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * The Class SessionUtils.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionUtils {

    public static final String COMPANY_CODE = "companyCode";

    private static final Map<String, Locale> LOCALES = new ConcurrentHashMap<>();

    /**
     * Gets the username in session.
     *
     * @return the username in session
     */
    public static String getUsername() {

        if (SecurityContextHolder.getContext() != null
                && SecurityContextHolder.getContext().getAuthentication() != null) {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return null;
    }

    /**
     * Gets the user in session.
     *
     * @return the user in session
     */
    public static Usuario getUserInSession() {

        if (SecurityContextHolder.getContext() != null //
                && SecurityContextHolder.getContext().getAuthentication() != null //
                && SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal() instanceof final Usuario user) {
            return user;
        }
        return null;
    }

    /**
     * Gets the company code.
     *
     * @return the company code
     */
    public static String getCompanyCode() {

        return removeStart(TenantContext.getCurrentTenant(), COMPANY);
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public static Long getUserId() {

        final Usuario user = getUserInSession();
        if (user != null) {
            return user.getId();
        }
        return null;
    }

    /**
     * Gets the user code.
     *
     * @return the user code
     */
    public static String getUserCode() {

        final Usuario user = getUserInSession();
        if (user != null) {
            return user.getPassword();
        }
        return null;
    }

    /**
     * Gets the user locale.
     *
     * @return the user locale
     */
    public static Locale getUserLocale() {

        final Usuario user = getUserInSession();
        if (user != null) {
            final String language = user.getFirstname();
            if (language != null) {
                return LOCALES.computeIfAbsent(language, Locale::new);
            }
        }
        return LOCALE_SPAIN;
    }

    /**
     * Gets the user and company.
     *
     * @return the user and company
     */
    public static GeneralData getUserAndCompany() {

        final Usuario user = getUserInSession();
        final String companyCode = getCompanyCode();

        final GeneralData generalData = new GeneralData();
        if (user != null) {
            generalData.setUser(user);
        }
        generalData.setCompanyCode(companyCode);

        return generalData;
    }

    // -------------------------------------------------------------------------
    /**
     * Gets the general data.
     *
     * @return the general data
     */
    public static GeneralData getGeneralData() {
        return getGeneralData(ProcessContext.getCurrentProcess(), null);
    }

    /**
     * Gets the general data.
     *
     * @param processCode
     *            the process code
     * @return the general data
     */
    public static GeneralData getGeneralData(final String processCode) {
        return getGeneralData(processCode, null);
    }

    /**
     * Gets the general data.
     *
     * @param transactionType
     *            the transaction type
     * @return the general data
     */
    public static GeneralData getGeneralData(final TransactionType transactionType) {
        return getGeneralData(ProcessContext.getCurrentProcess(), transactionType);
    }

    /**
     * Gets the general data.
     *
     * @param processCode
     *            the process code
     * @param transactionType
     *            the transaction type
     * @return the general data
     */
    public static GeneralData getGeneralData(final String processCode, final TransactionType transactionType) {
        final GeneralData generalData = getUserAndCompany();
        generalData.setProcessCode(processCode);
        generalData.setTransactionType(transactionType);
        return generalData;
    }

    // -------------------------------------------------------------------------
    /**
     * Authenticate.
     *
     * @param userCode
     *            the user code
     * @return the general data
     */
    public static GeneralData authenticate(final String userCode) {

        return authenticate(userCode, null);
    }

    /**
     * Authenticate.
     *
     * @param userCode
     *            the user code
     * @param company
     *            the company
     * @return the general data
     */
    public static GeneralData authenticate(final String userCode, final String company) {

        return authenticate(userCode, company);
    }

    /**
     * Authenticate.
     *
     * @param generalData
     *            the general data
     */
    public static void authenticate(final GeneralData generalData) {

        if (generalData.getUser() != null) {
            authenticate(generalData.getUser(), generalData.getCompanyCode(), generalData.getProcessCode());
        } else {
            authenticate(generalData.getUserCode(), generalData.getCompanyCode(), generalData.getProcessCode());
        }
    }

    /**
     * Authenticate.
     *
     * @param userCode
     *            the user code
     * @param company
     *            the company
     * @param processCode
     *            the process code
     * @return the general data
     */
    public static GeneralData authenticate(final String userCode, final String company, final String processCode) {

        Usuario user = null;
        if (userCode != null) {
            user = new Usuario();
            user.setPassword(userCode);
        }
        return authenticate(user, company, processCode);
    }

    /**
     * Authenticate.
     *
     * @param user
     *            the user
     * @param company
     *            the company
     * @param processCode
     *            the process code
     * @return the general data
     */
    public static GeneralData authenticate(final Usuario user, String company, String processCode) {

        final GeneralData generalData = new GeneralData();
        if (user != null) {
            if (company == null) { // keep current tenant
                company = TenantContext.getCurrentCompanyCode();
            }
            if (processCode == null) { // keep current process
                processCode = ProcessContext.getCurrentProcess();
            }

            final Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, null,
                    user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            generalData.setUser(user);
        }
        if (company != null) {
            TenantContext.setCurrentTenant(company);
            generalData.setCompanyCode(TenantContext.getCompanyCode(company));
        }
        if (processCode != null) {
            ProcessContext.setCurrentProcess(processCode);
            generalData.setProcessCode(processCode);
        }
        return generalData;
    }

    // -------------------------------------------------------------------------
    /**
     * Gets the user details.
     *
     * @param authentication
     *            the authentication
     * @return the user details
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getUserDetails() {

        Map<String, Object> userDetails = null;
        final AbstractAuthenticationToken authentication = (AbstractAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();
        if (authentication != null) {
            final Object principal = authentication.getPrincipal();
            if (principal instanceof final Usuario user) {
                userDetails = (Map<String, Object>) user.getDetails();
                if (userDetails == null) {
                    userDetails = new HashMap<>();
                    user.setDetails(userDetails);
                }
            } else if (principal instanceof Map) {
                userDetails = (Map<String, Object>) principal;

            } else if (principal == null) {
                userDetails = new HashMap<>();
                authentication.setDetails(userDetails);
            }
        }
        return userDetails != null ? userDetails : new HashMap<>();
    }

}
