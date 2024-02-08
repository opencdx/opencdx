import { slowCypressDown } from 'cypress-slow-down'
// slowCypressDown()

describe('template spec', () => {
  it('passes', () => {
    cy.visit('http://localhost:3000/form-builder')
    cy.get('input[type=file]').selectFile('cypress/downloads/alpha.one-q.json',{force: true,action: 'drag-drop'})
    cy.get('#user-form-json').click()
    cy.get('#close-dialog').click()
    cy.get('#anf-statement-json').click()
    cy.get('#close-dialog').click()
    cy.get('input[type=checkbox]').click()
    // cy.get('input[role=comboxbox]').click()
    cy.get('[name="rulesets-select').parent().click()
    // cy.get('[data-testid=ExpandMoreIcon]').click()
    // cy.get('#panel1a-header').click()
    
    // cy.get('[name="rulesets-select').select('Business Rule - Validation - Validate user responses')
    // cy.findByRole('option', { name: /Business Rule - Validation - Validate user responses/i }).click()
    // cy.get('select').select('Business Rule - Validation - Validate user responses')


  })
})