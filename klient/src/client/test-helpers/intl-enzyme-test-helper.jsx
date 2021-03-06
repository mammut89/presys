/**
 * https://github.com/yahoo/react-intl/wiki/Testing-with-React-Intl
 *
 * Components using the react-intl module require access to the intl context.
 * This is not available when mounting single components in Enzyme.
 * These helper functions aim to address that.
 */

import React from 'react';
import { IntlProvider, intlShape } from 'react-intl';
import { mount, shallow } from 'enzyme';

// You can pass your messages to the IntlProvider. Optional: remove if unneeded.
const messages = require('messages/nb_NO');

// Create the IntlProvider to retrieve context for wrapping around.
const intlProvider = new IntlProvider({ locale: 'nb-NO', messages }, {});
const { intl } = intlProvider.getChildContext();

/**
 * When using React-Intl `injectIntl` on components, props.intl is required.
 */
function nodeWithIntlProp(node) {
    return React.cloneElement(node, { intl });
}

export function shallowWithIntl(node, { context } = {}) {
    return shallow(
        nodeWithIntlProp(node),
        {
            context: Object.assign({}, context, {intl}),
        }
    );
}

export function mountWithIntl(node, { context, childContextTypes } = {}) {
    return mount(
        nodeWithIntlProp(node),
        {
            context: Object.assign({}, context, {intl}),
            childContextTypes: Object.assign({}, { intl: intlShape }, childContextTypes)
        }
    );
}
