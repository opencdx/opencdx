import React from 'react';
import { render, screen } from '@testing-library/react';
import { ComponentID } from './ComponentID';

describe('ComponentID', () => {
  it('renders correctly', () => {
    render(<ComponentID register={() => {}} />);

  });

  it('renders with the provided componentId', () => {
    const componentId = 'test-component-id';
    render(<ComponentID register={() => {}} item={{ componentId }} />);
   
  });

  it('renders with a generated componentId if not provided', () => {
    render(<ComponentID register={() => {}} />);
   
  });

  it('renders with the provided index', () => {
    const index = 1;
    render(<ComponentID register={() => {}} index={index} />);
  });

  });