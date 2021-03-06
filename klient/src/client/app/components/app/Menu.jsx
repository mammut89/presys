import React from 'react';
import { Link } from 'react-router';
import { FormattedMessage } from 'react-intl';


import styles from './menu.less';


export const Menu = ({ children }) => <ul className={styles.menu}>{children}</ul>;

Menu.propTypes = {
  children: React.PropTypes.arrayOf(React.PropTypes.element).isRequired,
};

export const MenuItem = ({ link, textcode }) => <li>
  <Link to={link} activeClassName={styles.active}>
    <FormattedMessage id={textcode} />
  </Link>
</li>;

export const TextMenuItem = ({ link, text }) => <li>
  <Link to={link} activeClassName={styles.active}>
    <span>{text}</span>
  </Link>
</li>;

TextMenuItem.propTypes = {
  link: React.PropTypes.string.isRequired,
  text: React.PropTypes.string.isRequired,
};

MenuItem.propTypes = {
  link: React.PropTypes.string.isRequired,
  textcode: React.PropTypes.string.isRequired,
};

export default Menu;
