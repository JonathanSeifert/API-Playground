import React from "react";
import { render, unmountComponentAtNode } from "react-dom";
import App from "./App"; 

//Cleanup
let container = null;
beforeEach(() => {
  // setup a DOM element as a render target
  container = document.createElement("div");
  document.body.appendChild(container);
});

afterEach(() => {
  // cleanup on exiting
  unmountComponentAtNode(container);
  container.remove();
  container = null;
});


describe('App', () => {
  test('renders without crashing', async () => {
    render(<App/>, container);
  })
  test('has title', () => {
    render(<App/>, container);
    expect(container.querySelector(".title").textContent)
      .toBe("API - Playground");    
  })
})