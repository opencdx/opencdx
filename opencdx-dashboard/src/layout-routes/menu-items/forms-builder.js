// third-party
import { FormattedMessage } from 'react-intl';

// assets
// import { IconBrandNetbeans, IconPropeller } from '@tabler/icons-react';

// ==============================|| DOCUMENTATION MENU ITEMS ||============================== //

const formsbuilder = {
    id: 'forms-builder',
    title: <FormattedMessage id="forms-builder" />,
    type: 'group',
    children: [

        {
            id: 'forms-builder',
            title: <FormattedMessage id="forms-builder" />,
            type: 'collapse',
            children: [
                {
                    id: 'forms-designer',
                    title: <FormattedMessage id="form-designer" />,
                    type: 'item',
                    url: ''
                }
            ]
        },
    ]
};

export default formsbuilder;
