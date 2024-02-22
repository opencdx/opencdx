
describe('template spec', () => {
  it('passes', () => {
    cy.visit('http://localhost:3000/form-builder')
    cy.get('input[type=file]').selectFile('cypress/downloads/alpha.json',{force: true,action: 'drag-drop'})
    cy.get('#user-form-json').click()
    cy.get('#close-dialog').click()
    cy.get('#anf-statement-json').click()
    cy.get('#close-dialog').click()
    cy.get('input[type=checkbox]').click()
  })
})