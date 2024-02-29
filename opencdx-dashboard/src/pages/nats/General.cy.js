import React from 'react';
import General from './General';

describe('<General />', () => {
    it('renders', () => {
        // see: https://on.cypress.io/mounting-react
        cy.mount(<General />);
    });
});
