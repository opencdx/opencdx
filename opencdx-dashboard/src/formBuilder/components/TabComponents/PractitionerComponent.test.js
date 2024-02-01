import React from 'react';
import { render, screen } from '@testing-library/react';
import { PractitionerComponent } from './PractitionerComponent';

describe('PractitionerComponent', () => {
  it('renders correctly', () => {
    render(<PractitionerComponent />);
    // Add your assertions here to test if the component renders correctly
  });

  it('renders with the provided practitionerId', () => {
    const practitionerId = 'test-practitioner-id';
    render(<PractitionerComponent practitionerId={practitionerId} />);
    // Add your assertions here to test if the component renders with the provided practitionerId
  });

  it('renders with the provided practitionerName', () => {
    const practitionerName = 'test-practitioner-name';
    render(<PractitionerComponent practitionerName={practitionerName} />);
    // Add your assertions here to test if the component renders with the provided practitionerName
  });

  it('renders with the provided practitionerCodes', () => {
    const practitionerCodes = ['code1', 'code2', 'code3'];
    render(<PractitionerComponent practitionerCodes={practitionerCodes} />);
    // Add your assertions here to test if the component renders with the provided practitionerCodes
  });

  it('renders with the default options', () => {
    render(<PractitionerComponent />);
    // Add your assertions here to test if the component renders with the default options
  });
});