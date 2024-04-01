import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
  ],
  exports: [
    CommonModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
  ],
})
  
export class MaterialModule {}
