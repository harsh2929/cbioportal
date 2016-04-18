package org.mskcc.cbio.portal.authentication.ldap;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mskcc.cbio.portal.authentication.PortalUserDetails;
import org.mskcc.cbio.portal.dao.PortalUserDAO;
import org.mskcc.cbio.portal.model.User;
import org.mskcc.cbio.portal.model.UserAuthorities;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.SpringSecurityLdapTemplate;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

import com.google.inject.internal.Lists;

/**
 * Mapping between details from an LDAP server and the user name used for authentication.
 *
 * Pull first name, given name, and email from the LDAP server.
 *
 * @author Manuel Holtgrewe <manuel.holtgrewe@bihealth.de>
 */
public class LDAPUserDetailsContextMapper implements UserDetailsContextMapper {

    /** {@link Log} to use */
    private static final Log LOG = LogFactory.getLog(LDAPUserDetailsContextMapper.class);

    /** {@link DefaultSpringSecurityContextSource} with the LDAP connection */
    private DefaultSpringSecurityContextSource ldapServer;
    /** data access object to use for retrieving the authorities from the database */
    private PortalUserDAO portalUserDAO;

    /** base path to search users in */
    private String baseDn;
    /** attribute name to use for finding users in directory */
    private String usernameAttribute = "sAMAccountName";
    /** attribute name of the first / given name in directory */
    private String givenNameAttribute = "givenName";
    /** attribute name of the last name in the directory */
    private String lastNameAttribute = "sn";
    /** attribute name of the email address in the directory */
    private String emailAttribute = "mail";

    /** Default constructor */
    public LDAPUserDetailsContextMapper() {
    }

    /**
     * Initialize the {@link LDAPUserDetailsContextMapper} with the given {@link DefaultSpringSecurityContextSource} and
     * {@link PortalUserDAO}.
     */
    public LDAPUserDetailsContextMapper(DefaultSpringSecurityContextSource ldapServer, PortalUserDAO portalUserDAO) {
        this.ldapServer = ldapServer;
        this.portalUserDAO = portalUserDAO;
    }

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
            Collection<? extends GrantedAuthority> authorities) {
        // Query LDAP server for user details, including first/given name and email.
        SpringSecurityLdapTemplate ldapTemplate = new SpringSecurityLdapTemplate(ldapServer);
        // ArrayList<String> attributesToRetrieve = Lists.newArrayList("givenname", "sn", "mail");
        String query = MessageFormat.format("({0}='{'0'}')", usernameAttribute);
        DirContextOperations user = ldapTemplate.searchForSingleEntry(baseDn, query, new String[] { username });
        String givenName = (String) user.getAttributeSortedStringSet(givenNameAttribute).first();
        String lastName = (String) user.getAttributeSortedStringSet(lastNameAttribute).first();
        String email = (String) user.getAttributeSortedStringSet(emailAttribute).first();
        email = email.toLowerCase();

        // Construct the resulting PortalUserDetails object.
        PortalUserDetails result = new PortalUserDetails(username, getGrantedAuthorities(email));
        result.setEmail(email);
        result.setName(MessageFormat.format("{0} {1}", givenName, lastName));
        return result;
    }

    /**
     * Retrieve List of {@link GrantedAuthority} objects for a user's email.
     *
     * This list is loaded from the cBioPortal database.
     *
     * @param email
     *            email to use for the querying
     * @return resulting list of granted authorities
     */
    private List<GrantedAuthority> getGrantedAuthorities(String email) {
        User user = null;
        try {
            user = portalUserDAO.getPortalUser(email);
        } catch (Exception e) {
            // ignore, handle below with user == null
        }

        if (user == null || !user.isEnabled()) {
            LOG.debug("user not found or not enabled " + email);
            return AuthorityUtils.createAuthorityList(new String[0]);
        } else {
            LOG.debug("getGrantedAuthorities(), attempting to fetch portal user authorities, email: " + email);
            UserAuthorities authorities = null;
            try {
                authorities = portalUserDAO.getPortalUserAuthorities(email);
            } catch (Exception e) {
                LOG.debug("problem with database query:" + e.getMessage());
                throw new UsernameNotFoundException("Could not retrieve authorities for " + email, e);
            }
            if (authorities != null)
                return AuthorityUtils.createAuthorityList(
                        authorities.getAuthorities().toArray(new String[authorities.getAuthorities().size()]));
            else
                return AuthorityUtils.createAuthorityList(new String[0]);
        }
    }

    @Override
    public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
        throw new UnsupportedOperationException();
    }

    /** @return {@link DefaultSpringSecurityContextSource} with the LDAP connection */
    public DefaultSpringSecurityContextSource getLdapServer() {
        return ldapServer;
    }

    /** Set {@link DefaultSpringSecurityContextSource} with the LDAP connection */
    public void setLdapServer(DefaultSpringSecurityContextSource ldapServer) {
        this.ldapServer = ldapServer;
    }

    /** @return data access object to use for retrieving the authorities from the database */
    public PortalUserDAO getPortalUserDAO() {
        return portalUserDAO;
    }

    /** Set data access object to use for retrieving the authorities from the database */
    public void setPortalUserDAO(PortalUserDAO portalUserDAO) {
        this.portalUserDAO = portalUserDAO;
    }

    /** @return base path to search users in */
    public String getBaseDn() {
        return baseDn;
    }

    /** Set base path to search users in */
    public void setBaseDn(String baseDn) {
        this.baseDn = baseDn;
    }

    /** @return username attribute name to use for finding users in directory */
    public String getUsernameAttribute() {
        return usernameAttribute;
    }

    /** Set username attribute name to use for finding users in directory */
    public void setUsernameAttribute(String usernameAttribute) {
        this.usernameAttribute = usernameAttribute;
    }

    /** @return attribute name of the first / given name in directory */
    public String getGivenNameAttribute() {
        return givenNameAttribute;
    }

    /** Set attribute name of the first / given name in directory */
    public void setGivenNameAttribute(String givenNameAttribute) {
        this.givenNameAttribute = givenNameAttribute;
    }

    /** @return attribute name of the last name in the directory */
    public String getLastNameAttribute() {
        return lastNameAttribute;
    }

    /** Set attribute name of the last name in the directory */
    public void setLastNameAttribute(String lastNameAttribute) {
        this.lastNameAttribute = lastNameAttribute;
    }

    /** @return attribute name of the email address in the directory */
    public String getEmailAttribute() {
        return emailAttribute;
    }

    /** Set attribute name of the email address in the directory */
    public void setEmailAttribute(String emailAttribute) {
        this.emailAttribute = emailAttribute;
    }

}
