/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FrTestModule } from '../../../test.module';
import { PrecintoDetailComponent } from '../../../../../../main/webapp/app/entities/precinto/precinto-detail.component';
import { PrecintoService } from '../../../../../../main/webapp/app/entities/precinto/precinto.service';
import { Precinto } from '../../../../../../main/webapp/app/entities/precinto/precinto.model';

describe('Component Tests', () => {

    describe('Precinto Management Detail Component', () => {
        let comp: PrecintoDetailComponent;
        let fixture: ComponentFixture<PrecintoDetailComponent>;
        let service: PrecintoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [PrecintoDetailComponent],
                providers: [
                    PrecintoService
                ]
            })
            .overrideTemplate(PrecintoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrecintoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrecintoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Precinto(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.precinto).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
