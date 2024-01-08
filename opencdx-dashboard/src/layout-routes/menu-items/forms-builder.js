// third-party
import { FormattedMessage } from 'react-intl';

// assets

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
                    title: <FormattedMessage id="forms-designer" />,
                    type: 'item',
                    target: true,
                    url: '/form-builder'
                },
                {
                    id: 'forms-viewer',
                    title: <FormattedMessage id="forms-viewer" />,
                    type: 'item',
                    url: '/pages/form-viewer'
                }
            ]
        }
    ]
};

export default formsbuilder;
