<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<meta charset="UTF-8" />
<title>login</title>
<%@ include file="header.jsp"%>
<link href="https://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.js"></script>
</head>
<body>
<form id="target" class="form-horizontal" style="background-color: rgb(255, 255, 255);">
                <fieldset>
                  <div id="legend" class="component" rel="popover" trigger="manual" data-content="
                    <form class='form'>
                      <div class='controls'>
                        <label class='control-label'>Title</label> <input class='input-large' type='text' name='title' id='text'>
                        <hr/>
                        <button class='btn btn-info'>Save</button><button class='btn btn-danger'>Cancel</button>
                      </div>
                    </form>" data-original-title="Form Title" style="border-top: 1px solid white; border-bottom: none;">
                    <legend class="valtype" data-valtype="text">表单名00</legend>
                  </div>
                

    <div class="control-group component" data-type="text" rel="popover" trigger="manual" data-content="
                      <form class='form'>
                        <div class='controls'>
                          <label class='control-label'>Label Text</label> <input class='input-large' type='text' name='label' id='label'>
                          <label class='control-label'>Placeholder</label> <input type='text' name='placeholder' id='placeholder'>
                          <label class='control-label'>Help Text</label> <input type='text' name='help' id='help'>
                          <hr/>
                          <button class='btn btn-info'>Save</button><button class='btn btn-danger'>Cancel</button>
                        </div>
                      </form>" data-original-title="Text Input" style="border-top: 1px solid white; border-bottom: none;">

                      <!-- Text input-->
                      <label class="control-label valtype" for="input01" data-valtype="label">Text input</label>
                      <div class="controls">
                        <input type="text" placeholder="placeholder" class="input-xlarge valtype" data-valtype="placeholder">
                        <p class="help-block valtype" data-valtype="help">Supporting help text</p>
                      </div>
                    </div>

    

    <div class="control-group component" rel="popover" trigger="manual" data-content="
                      <form class='form'>
                        <div class='controls'>
                          <label class='control-label'>Label Text</label> <input class='input-large' type='text' name='label' id='label'>
                          <label class='control-label'>Options: </label>
                          <textarea style='min-height: 200px' id='checkboxes'> </textarea>
                          <hr/>
                          <button class='btn btn-info'>Save</button><button class='btn btn-danger'>Cancel</button>
                        </div>
                      </form>" data-original-title="Multiple Checkboxes" style="border-top: 1px solid white; border-bottom: none;">
                      <label class="control-label valtype" data-valtype="label">Checkboxes</label>
                      <div class="controls valtype" data-valtype="checkboxes">

                        <!-- Multiple Checkboxes -->
                        <label class="checkbox">
                          <input type="checkbox" value="Option one">
                          Option one
                        </label>
                        <label class="checkbox">
                          <input type="checkbox" value="Option two">
                          Option two
                        </label>
                      </div>

                    </div><div class="control-group component" rel="popover" trigger="manual" data-content="
                      <form class='form'>
                        <div class='controls'>
                          <label class='control-label'>Label Text</label> <input class='input-large' type='text' name='label' id='label'>
                          <label class='control-label'>Options: </label>
                          <textarea style='min-height: 200px' id='option'> </textarea>
                          <hr/>
                          <button class='btn btn-info'>Save</button><button class='btn btn-danger'>Cancel</button>
                        </div>
                      </form>" data-original-title="Search Input" style="border-top: 1px solid white; border-bottom: none;">

                      <!-- Select Basic -->
                      <label class="control-label valtype" data-valtype="label">Select - Basic</label>
                      <div class="controls">
                        <select class="input-xlarge valtype" data-valtype="option">
                          <option>Enter</option>
                          <option>Your</option>
                          <option>Options</option>
                          <option>Here!</option>
                        </select>
                      </div>

                    </div><div class="control-group component" data-type="prep-text" rel="popover" trigger="manual" data-content="
                      <form class='form'>
                        <div class='controls'>
                          <label class='control-label'>Label Text</label> <input class='input-large' type='text' name='label' id='label'>
                          <label class='control-label'>Prepend</label> <input type='text' name='prepend' id='prepend'>
                          <label class='control-label'>Placeholder</label> <input type='text' name='placeholder' id='placeholder'>
                          <label class='control-label'>Help Text</label> <input type='text' name='help' id='help'>
                          <hr/>
                          <button class='btn btn-info'>Save</button><button class='btn btn-danger'>Cancel</button>
                        </div>
                      </form>" data-original-title="Prepended Text Input" style="border-top: 1px solid white; border-bottom: none;">

                      <!-- Prepended text-->
                      <label class="control-label valtype" data-valtype="label">Prepended text</label>
                      <div class="controls">
                        <div class="input-prepend">
                          <span class="add-on valtype" data-valtype="prepend">^_^</span>
                          <input class="span2 valtype" placeholder="placeholder" id="prependedInput" type="text" data-valtype="placeholder">
                        </div>
                        <p class="help-block valtype" data-valtype="help">Supporting help text</p>
                      </div>

                    </div>

    <div class="control-group component" rel="popover" trigger="manual" data-content="
                      <form class='form'>
                        <div class='controls'>
                          <label class='control-label'>Label Text</label> <input class='input-large' type='text' name='label' id='label'>
                          <label class='control-label'>Options: </label>
                          <textarea style='min-height: 200px' id='option'></textarea>
                          <hr/>
                          <button class='btn btn-info'>Save</button><button class='btn btn-danger'>Cancel</button>
                        </div>
                      </form>" data-original-title="Search Input" style="border-top: 1px solid white; border-bottom: none;">

                      <!-- Select Multiple -->
                      <label class="control-label valtype" data-valtype="label">Select - Multiple</label>
                      <div class="controls">
                        <select class="input-xlarge valtype" multiple="multiple" data-valtype="option">
                          <option>Enter</option>
                          <option>Your</option>
                          <option>Options</option>
                          <option>Here!</option>
                        </select>
                      </div>
                    </div><div class="control-group component" rel="popover" trigger="manual" data-content="
                      <form class='form'>
                        <div class='controls'>
                          <label class='control-label'>Label Text</label> <input class='input-large' type='text' name='label' id='label'>
                          <label class='control-label'>Placeholder</label> <input type='text' name='placeholder' id='placeholder'>
                          <label class='control-label'>Help Text</label> <input type='text' name='help' id='help'>
                          <label class='checkbox'><input type='checkbox' class='input-inline' name='checked' id='checkbox'>Checked</label>
                          <hr/>
                          <button class='btn btn-info'>Save</button><button class='btn btn-danger'>Cancel</button>
                        </div>
                      </form>" data-original-title="Search Input" style="border-top: 1px solid white; border-bottom: none;">

                      <!-- Appended checkbox -->
                      <label class="control-label valtype" data-valtype="label">Append checkbox</label>
                      <div class="controls">
                        <div class="input-append">
                          <input class="span2 valtype" placeholder="placeholder" type="text" data-valtype="placeholder">
                          <span class="add-on">
                            <label class="checkbox" for="appendedCheckbox">
                              <input type="checkbox" class="valtype" data-valtype="checkbox">
                            </label>
                          </span>
                        </div>
                        <p class="help-block valtype" data-valtype="help">Supporting help text</p>
                      </div>
                    </div>

    <div class="control-group component" rel="popover" trigger="manual" data-content="
                      <form class='form'>
                        <div class='controls'>
                          <label class='control-label'>Label Text</label> <input class='input-large' type='text' name='label' id='label'>
                          <label class='control-label'>Button Text</label> <input class='input-large' type='text' name='label' id='button'>
                          <label class='control-label' id=''>Type: </label>
                          <select class='input' id='color'>
                            <option id='btn-default'>Default</option>
                            <option id='btn-primary'>Primary</option>
                            <option id='btn-info'>Info</option>
                            <option id='btn-success'>Success</option>
                            <option id='btn-warning'>Warning</option>
                            <option id='btn-danger'>Danger</option>
                            <option id='btn-inverse'>Inverse</option>
                          </select>
                          <hr/>
                          <button class='btn btn-info'>Save</button><button class='btn btn-danger'>Cancel</button>
                        </div>
                      </form>" data-original-title="Search Input" style="border-top: 1px solid white; border-bottom: none;">
                      <label class="control-label valtype" data-valtype="label">Button</label>

                      <!-- Button -->
                      <div class="controls valtype" data-valtype="button">
                        <button class="btn btn-success">Button</button>
                      </div>
                    </div><div class="control-group component" rel="popover" trigger="manual" data-content="
                      <form class='form'>
                        <div class='controls'>
                          <label class='control-label'>Label Text</label> <input class='input-large' type='text' name='label' id='label'>
                          <label class='control-label'>Options: </label>
                          <textarea style='min-height: 200px' id='checkboxes'> </textarea>
                          <hr/>
                          <button class='btn btn-info'>Save</button><button class='btn btn-danger'>Cancel</button>
                        </div>
                      </form>" data-original-title="Multiple Checkboxes" style="border-top: 1px solid white; border-bottom: none;">
                      <label class="control-label valtype" data-valtype="label">Checkboxes</label>
                      <div class="controls valtype" data-valtype="checkboxes">

                        <!-- Multiple Checkboxes -->
                        <label class="checkbox">
                          <input type="checkbox" value="Option one">
                          Option one
                        </label>
                        <label class="checkbox">
                          <input type="checkbox" value="Option two">
                          Option two
                        </label>
                      </div>

                    </div><div class="control-group component" rel="popover" trigger="manual" data-content="
                      <form class='form'>
                        <div class='controls'>
                          <label class='control-label'>Label Text</label> <input class='input-large' type='text' name='label' id='label'>
                          <label class='control-label'>Group Name Attribute</label> <input class='input-large' type='text' name='name' id='name'>
                          <textarea style='min-height: 200px' id='inline-radios'></textarea>
                          <hr/>
                          <button class='btn btn-info'>Save</button><button class='btn btn-danger'>Cancel</button>
                        </div>
                      </form>" data-original-title="Inline radioes" style="border-top: 1px solid white; border-bottom: none;">
                      <label class="control-label valtype" data-valtype="label">Inline radios</label>
                      <div class="controls valtype" data-valtype="inline-radios">

                        <!-- Inline Radios -->
                        <label class="radio inline">
                          <input type="radio" value="1" checked="checked" name="group">
                          1
                        </label>
                        <label class="radio inline">
                          <input type="radio" value="2" name="group">
                          2
                        </label>
                        <label class="radio inline">
                          <input type="radio" value="3">
                          3
                        </label>
                      </div>
                    </div><div class="control-group component" rel="popover" trigger="manual" data-content="
                      <form class='form'>
                        <div class='controls'>
                          <label class='control-label'>Label Text</label> <input class='input-large' type='text' name='label' id='label'>
                          <textarea style='min-height: 200px' id='inline-checkboxes'></textarea>
                          <hr/>
                          <button class='btn btn-info'>Save</button><button class='btn btn-danger'>Cancel</button>
                        </div>
                      </form>" data-original-title="Inline Checkboxes" style="border-top: 1px solid white; border-bottom: none;">
                      <label class="control-label valtype" data-valtype="label">Inline Checkboxes</label>

                      <!-- Multiple Checkboxes -->
                      <div class="controls valtype" data-valtype="inline-checkboxes">
                        <label class="checkbox inline">
                          <input type="checkbox" value="1"> 1
                        </label>
                        <label class="checkbox inline">
                          <input type="checkbox" value="2"> 2
                        </label>
                        <label class="checkbox inline">
                          <input type="checkbox" value="3"> 3
                        </label>
                      </div>

                    </div><div class="control-group component" rel="popover" trigger="manual" data-content="
                      <form class='form'>
                        <div class='controls'>
                          <label class='control-label'>Label Text</label> <input class='input-large' type='text' name='label' id='label'>
                          <label class='control-label'>Placeholder</label> <input type='text' name='placeholder' id='placeholder'>
                          <label class='control-label'>Help Text</label> <input type='text' name='help' id='help'>
                          <label class='checkbox'><input type='checkbox' class='input-inline' name='checked' id='checkbox'>Checked</label>
                          <hr/>
                          <button class='btn btn-info'>Save</button><button class='btn btn-danger'>Cancel</button>
                        </div>
                      </form>" data-original-title="Search Input" style="border-top: 1px solid white; border-bottom: none;">

                      <!-- Prepended checkbox -->
                      <label class="control-label valtype" data-valtype="label">Prepended checkbox</label>
                      <div class="controls">
                        <div class="input-prepend">
                          <span class="add-on">
                            <label class="checkbox">
                              <input type="checkbox" class="valtype" data-valtype="checkbox">
                            </label>
                          </span>
                          <input class="span2 valtype" placeholder="placeholder" type="text" data-valtype="placeholder">
                        </div>
                        <p class="help-block valtype" data-valtype="help">Supporting help text</p>
                      </div>

                    </div>

    

    

    <div class="control-group component" data-type="text" rel="popover" trigger="manual" data-content="
                      <form class='form'>
                        <div class='controls'>
                          <label class='control-label'>Label Text</label> <input class='input-large' type='text' name='label' id='label'>
                          <label class='control-label'>Placeholder</label> <input type='text' name='placeholder' id='placeholder'>
                          <label class='control-label'>Help Text</label> <input type='text' name='help' id='help'>
                          <hr/>
                          <button class='btn btn-info'>Save</button><button class='btn btn-danger'>Cancel</button>
                        </div>
                      </form>" data-original-title="Text Input" style="border-top: 1px solid white; border-bottom: none;">

                      <!-- Text input-->
                      <label class="control-label valtype" for="input01" data-valtype="label">Text input</label>
                      <div class="controls">
                        <input type="text" placeholder="placeholder" class="input-xlarge valtype" data-valtype="placeholder">
                        <p class="help-block valtype" data-valtype="help">Supporting help text</p>
                      </div>
                    </div>

    </fieldset>
              </form>
</body>
</html>