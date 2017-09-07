import React from 'react';
import styles from './infoTable.less';


const InfoTable = ({ children }) => (
  <table className={styles.infotable}><tbody>
    {children}
  </tbody></table>);

InfoTable.propTypes = {
  children: React.PropTypes.number.isRequired,
};

export default InfoTable;

