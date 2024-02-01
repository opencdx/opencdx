import React from 'react';
import { render, screen } from '@testing-library/react';
import {TextArea} from './TextArea';

describe('TextArea', () => {
  it('renders correctly', () => {
    render(<TextArea />);
    // Add your assertions here to test the rendering of the component
  });

  it('renders with the provided value', () => {
    const value = 'Test value';
    render(<TextArea value={value} />);
    // Add your assertions here to test the rendering of the component with the provided value
  });

  it('renders with the provided placeholder', () => {
    const placeholder = 'Test placeholder';
    render(<TextArea placeholder={placeholder} />);
    // Add your assertions here to test the rendering of the component with the provided placeholder
  });

  // Add more test cases as needed

});