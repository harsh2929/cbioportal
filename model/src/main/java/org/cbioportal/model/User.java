/*
 * Copyright (c) 2015 Memorial Sloan-Kettering Cancer Center.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY OR FITNESS
 * FOR A PARTICULAR PURPOSE. The software and documentation provided hereunder
 * is on an "as is" basis, and Memorial Sloan-Kettering Cancer Center has no
 * obligations to provide maintenance, support, updates, enhancements or
 * modifications. In no event shall Memorial Sloan-Kettering Cancer Center be
 * liable to any party for direct, indirect, special, incidental or
 * consequential damages, including lost profits, arising out of the use of this
 * software and its documentation, even if Memorial Sloan-Kettering Cancer
 * Center has been advised of the possibility of such damage.
 */

/*
 * This file is part of cBioPortal.
 *
 * cBioPortal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.cbioportal.model;

import java.io.Serializable;

/**
 * This represents a user, identified by an email address.
 * 
 * @author Arthur Goldberg goldberg@cbio.mskcc.org
 * @author Benjamin Gross
 */
public class User implements Serializable {
    private String email;
    private String name;
    private boolean enabled;

    public User(String email, String name, boolean enabled) {
        if (email == null) {
            throw new IllegalArgumentException("email is null.");
        }
        if (name == null) {
            throw new IllegalArgumentException("name is null.");
        }
        this.email = email;
        this.name = name;
        this.enabled = enabled;
    }

    public User(String email, String name, Boolean enabled) {
        this(email, name, enabled != null && enabled);
    }

    /**
     * Gets the user's email.
     *
     * @return The user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     *
     * @param email The user's email.
     */
    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    /**
     * Gets the user's name.
     *
     * @return The user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name.
     *
     * @param name The user's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns true if the user is enabled, false otherwise.
     *
     * @return Whether or not the user is enabled.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets whether or not the user is enabled.
     *
     * @param enabled Whether or not the user is enabled.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object otherUser) {
        if (this == otherUser) {
            return true;
        }
        if (otherUser == null || getClass() != otherUser.getClass()) {
            return false;
        }
        User user = (User) otherUser;
        return Objects.equals(email, user.email) &&
               Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name);
    }
}
