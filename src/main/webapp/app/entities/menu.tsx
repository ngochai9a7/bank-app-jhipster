import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/loan">
        <Translate contentKey="global.menu.entities.loan" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/loan-type">
        <Translate contentKey="global.menu.entities.loanType" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/customer">
        <Translate contentKey="global.menu.entities.customer" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/branch">
        <Translate contentKey="global.menu.entities.branch" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/accounts">
        <Translate contentKey="global.menu.entities.accounts" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/account-type">
        <Translate contentKey="global.menu.entities.accountType" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/transaction">
        <Translate contentKey="global.menu.entities.transaction" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/transaction-type">
        <Translate contentKey="global.menu.entities.transactionType" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
